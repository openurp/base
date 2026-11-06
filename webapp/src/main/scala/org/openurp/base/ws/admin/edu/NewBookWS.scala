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

package org.openurp.base.ws.admin.edu

import org.beangle.commons.json.JsonObject
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.annotation.action
import org.openurp.base.edu.model.Textbook
import org.openurp.base.model.Project
import org.openurp.base.ws.admin.ProjectAdminJsonWS
import org.openurp.code.sin.model.{Press, PressGrade}

import java.time.{Instant, LocalDate}

/** 新增教材。/api/base/admin/edu/new-books */
@action("new-books")
class NewBookWS extends ProjectAdminJsonWS[Textbook] {

  override def getQueryBuilder: OqlBuilder[Textbook] = {
    val query = super.getQueryBuilder
    get("q") foreach { keyword =>
      if (Strings.isNotEmpty(keyword)) {
        query.where("textbook.isbn like :q or textbook.name like :q or textbook.author like :q", "%" + keyword + "%")
      }
    }
    query
  }

  override protected def prepare(book: Textbook): Unit = {
    given project: Project = getProject
    book.project = project
    book.beginOn = LocalDate.now
    book.creator = Some(getUser)
    val lecture = getBoolean("lecture", false)
    if (lecture) {
      book.author = getUser.name
      book.madeInSchool = true
      book.edition = "1"
    }
    get("press.name") foreach { p =>
      if (Strings.isNotBlank(p)) {
        val pn = p.trim()
        val q = OqlBuilder.from(classOf[Press], "p").where("p.name=:name or p.name like :name2", pn, pn + "%")
        val presses = entityDao.search(q)
        if (presses.size == 1) book.press = presses.headOption
        else {
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
    if (book.domestic) book.foreignBookType = None
    if (book.isbn.nonEmpty && entityDao.duplicate(classOf[Textbook], book.id, Map("isbn" -> book.isbn))) {
      throw new IllegalArgumentException("ISBN 重复")
    }
  }

  override protected def persist(book: Textbook): JsonObject = {
    val json = super.persist(book)
    businessLogger.info(s"新增了教材:${book.name} ${book.author}", book.id, org.beangle.webmvc.context.ActionContext.current.params)
    json
  }
}
