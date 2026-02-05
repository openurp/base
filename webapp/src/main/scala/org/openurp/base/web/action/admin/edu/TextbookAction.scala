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

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.transfer.importer.ImportSetting
import org.beangle.transfer.importer.listener.ForeignerListener
import org.beangle.ems.app.Ems
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.base.edu.model.{CourseTextbook, Textbook}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{TextbookHelper, TextbookImportListener}
import org.openurp.code.Code
import org.openurp.code.edu.model.{BookAwardType, BookType, DisciplineCategory}
import org.openurp.code.sin.model.*

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate, YearMonth}

class TextbookAction extends ProjectRestfulAction[Textbook], ExportSupport[Textbook], ImportSupport[Textbook] {

  def innerIndex(): View = {
    forward()
  }

  private def getCodeNames[T <: Code](clazz: Class[T])(using project: Project): collection.Seq[String] = {
    val codes = getCodes(clazz)
    codes.sortBy(_.code).map(x => x.code + " " + x.name)
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val presses = getCodeNames(classOf[Press])
    val categories = getCodeNames(classOf[BookCategory])
    val bookTypes = getCodeNames(classOf[BookType])
    val awardTypes = getCodeNames(classOf[BookAwardType])
    val foreignBookTypes = getCodeNames(classOf[BookAwardType])
    val bookForms = getCodeNames(classOf[TextbookForm])
    val disciplineCategories = getCodeNames(classOf[DisciplineCategory])

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("教材信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("ISBN", "textbook.isbn").length(13).required().remark("≤13位")
    sheet.add("名称", "textbook.name").length(100).required()
    sheet.add("作者", "textbook.author").length(50).required()
    sheet.add("译者", "textbook.translator").length(50)
    sheet.add("出版社", "textbook.press.code").ref(presses).required()
    sheet.add("版次", "textbook.edition").length(20).required()
    sheet.add("出版年月", "textbook.publishedIn").date("YYYY-MM").required()
    sheet.add("是否自编", "textbook.madeInSchool").bool()
    sheet.add("教材类型", "textbook.bookType.code").ref(bookTypes)
    sheet.add("图书分类", "textbook.category.code").ref(categories)
    sheet.add("获奖级别", "textbook.awardType.code").ref(awardTypes)
    sheet.add("颁奖单位", "textbook.awardOrg").length(50)
    sheet.add("丛书", "textbook.series").length(100)
    sheet.add("价格", "textbook.price").decimal()

    sheet.add("是否境内教材", "textbook.domestic").bool()
    sheet.add("境外教材类型", "textbook.foreignBookType.code").ref(foreignBookTypes)
    sheet.add("教材形态", "textbook.bookForm.code").ref(bookForms)
    sheet.add("学科门类", "textbook.disciplineCategory.code").ref(disciplineCategories)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教材模板.xlsx")
  }

  override protected def indexSetting(): Unit = {
    put("project", getProject)
    super.indexSetting()
  }

  protected override def editSetting(textbook: Textbook): Unit = {
    given project: Project = getProject

    put("bookTypes", getCodes(classOf[BookType]))
    put("presses", getCodes(classOf[Press]))
    put("awardTypes", getCodes(classOf[BookAwardType]))
    put("categories", getCodes(classOf[BookCategory]))
    put("foreignBookTypes", getCodes(classOf[ForeignBookType]))
    put("textbookForms", getCodes(classOf[TextbookForm]))
    put("disciplineCategories", getCodes(classOf[DisciplineCategory]))
    put("project", project)
    put("Ems", Ems)
    if (!textbook.persisted) textbook.beginOn = LocalDate.now()
  }

  override protected def getQueryBuilder: OqlBuilder[Textbook] = {
    val builder: OqlBuilder[Textbook] = super.getQueryBuilder
    getBoolean("active") foreach {
      case true =>
        builder.where("textbook.endOn is null or textbook.endOn >=:now", LocalDate.now)
      case false =>
        builder.where("textbook.endOn is not null and textbook.endOn <:now", LocalDate.now)
    }

    builder.limit(getPageLimit)
    builder
  }

  protected override def saveAndRedirect(book: Textbook): View = {
    //去除isbn中的横线
    if (book.isbn.nonEmpty && Strings.isNotBlank(book.isbn.get)) {
      book.isbn = Some(formatISBN(book.isbn.get))
    }
    if null == book.beginOn then book.beginOn = LocalDate.now
    if (book.isbn.nonEmpty && entityDao.duplicate(classOf[Textbook], book.id, Map("isbn" -> book.isbn))) {
      addError("ISBN 重复")
      put("textbook", book)
      editSetting(book)
      forward("form")
    } else {
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
      super.saveAndRedirect(book)
    }
  }

  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val textbook = entityDao.get(classOf[Textbook], id.toLong)
    val cq = OqlBuilder.from(classOf[CourseTextbook], "ct")
    cq.where("ct.textbook=:book", textbook)
    val courses = entityDao.search(cq)
    put("textbook", textbook)
    put("courses", entityDao.search(cq).map(_.course).distinct)
    forward()
  }

  def batchAddForm(): View = {
    get("isbn") foreach { l =>
      val isbns = Strings.split(l)
      val helper = TextbookHelper
      val datas = isbns.map { isbn => helper.fetchByIsbn(isbn) }
      put("isbnList", isbns)
      put("isbnDatas", datas)
    }
    forward()
  }

  def batchAdd(): View = {
    var newBookCount = 0
    val helper = TextbookHelper

    given project: Project = getProject

    getAll("book.isbn", classOf[String]) foreach { i =>
      val isbn = formatISBN(i.trim())
      if (!entityDao.duplicate(classOf[Textbook], null, Map("isbn" -> isbn, "project" -> project))) {
        val data = helper.fetchByIsbn(isbn)
        if data.contains("name") then {
          val book = new Textbook
          book.isbn = Some(data.getString("isbn"))
          book.name = data.getString("name")
          book.author = data.getString("author")
          book.edition = data.getString("edition")
          book.publishedIn = YearMonth.parse(data.getString("publishedIn"))

          book.project = project
          book.beginOn = LocalDate.now
          book.creator = Some(getUser)
          val p = data.getString("press")
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
          //如果是境内教材，取消境外教材类型
          if (book.domestic) {
            book.foreignBookType = None
          }
          newBookCount += 1
          entityDao.saveOrUpdate(book)
        }
      }
    }
    redirect("search", s"成功导入${newBookCount}条教材")
  }

  private def formatISBN(isbn: String): String = {
    if (isbn.length > 13) {
      Strings.replace(isbn, "-", "")
    } else {
      isbn
    }
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TextbookImportListener(getProject, entityDao))
  }
}
