/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.base.web.action.admin.edu

import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.lang.Strings
import org.beangle.commons.net.http.HttpUtils
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.ems.app.web.WebBusinessLogger
import org.beangle.security.Securities
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.Textbook
import org.openurp.base.model.Project
import org.openurp.base.web.helper.TextbookHelper
import org.openurp.code.edu.model.{BookAwardType, BookType, DisciplineCategory}
import org.openurp.code.sin.model.*
import org.openurp.starter.web.support.ProjectSupport

import java.time.{Instant, LocalDate}

/** 新增教材
 */
class NewBookAction extends ActionSupport, EntityAction[Textbook], ProjectSupport {
  var entityDao: EntityDao = _

  var businessLogger: WebBusinessLogger = _

  def index(): View = {
    put("project", getProject)
    put("me", Securities.user)
    forward()
  }

  @mapping(value = "new", view = "newBook")
  def editNew(): View = {
    val textbook = new Textbook

    given project: Project = entityDao.get(classOf[Project], getIntId("project"))

    put("bookTypes", getCodes(classOf[BookType]))
    put("presses", getCodes(classOf[Press]))
    put("awardTypes", getCodes(classOf[BookAwardType]))
    put("categories", getCodes(classOf[BookCategory]))
    put("foreignBookTypes", getCodes(classOf[ForeignBookType]))
    put("textbookForms", getCodes(classOf[TextbookForm]))
    put("disciplineCategories", getCodes(classOf[DisciplineCategory]))
    if (!textbook.persisted) textbook.beginOn = LocalDate.now()
    put("project", project)
    put("textbook", textbook)
    val lecture = getBoolean("lecture", false)
    if (lecture) {
      textbook.author = getUser.name
      textbook.madeInSchool = true
      textbook.edition = "1"
    }
    if lecture then forward("newLecture") else forward("newBook")
  }

  def save(): View = {
    given project: Project = entityDao.get(classOf[Project], getIntId("project"))

    val book = populateEntity(classOf[Textbook], "textbook")
    book.project = project
    book.beginOn = LocalDate.now
    book.creator = Some(getUser)
    
    get("press.name") foreach { p =>
      if (Strings.isNotBlank(p)) {
        val pn = p.trim()
        val q = OqlBuilder.from(classOf[Press], "p").where("p.name=:name or p.name like :name2", pn, pn + "%")
        val presses = entityDao.search(q)
        if (presses.size == 1) {
          book.press = presses.headOption
        } else {
          val np = new Press
          np.name = pn
          np.code = pn
          np.beginOn = LocalDate.now
          np.grade = new PressGrade(PressGrade.Other)
          np.updatedAt = Instant.now
          entityDao.saveOrUpdate(np)
          book.press = Some(np)
        }
      }
    }
    
    //如果是境内教材，取消境外教材类型
    if (book.domestic) {
      book.foreignBookType = None
    }

    if (book.isbn.nonEmpty && entityDao.duplicate(classOf[Textbook], book.id, Map("isbn" -> book.isbn))) {
      addError("ISBN 重复")
      editNew()
      forward("newBook")
    } else {
      entityDao.saveOrUpdate(book)
      businessLogger.info(s"新增了教材:${book.name} ${book.author}", book.id, ActionContext.current.params)
    }
    redirect("info", s"id=${book.id}", "添加成功")
  }

  @mapping(value = "{id}")
  def info(@param("id") id: String): View = {
    val book = entityDao.get(classOf[Textbook], id.toLong)
    put("textbook", book)
    forward()
  }

  def search(): View = {
    val q = OqlBuilder.from(classOf[Textbook], "textbook")
    populateConditions(q)
    get("q") foreach { i =>
      if (Strings.isNotEmpty(i)) {
        q.where("textbook.isbn like :q or textbook.name like :q or textbook.author like :q", "%" + i + "%")
      }
    }
    QueryHelper.limit(q)
    QueryHelper.sort(q)
    put("textbooks", entityDao.search(q))
    forward()
  }

  @response
  def queryByIsbn(): JsonObject = {
    val isbn = get("isbn").orNull
    TextbookHelper.fetchByIsbn(isbn)
  }
}
