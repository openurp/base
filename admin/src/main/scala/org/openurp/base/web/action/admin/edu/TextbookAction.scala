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
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.{mapping, param, response}
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.openurp.base.edu.model.{Course, Textbook}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.TextbookImportListener
import org.openurp.code.edu.model.{BookAwardType, BookType}
import org.openurp.code.sin.model.{BookCategory, Press}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

class TextbookAction extends ProjectRestfulAction[Textbook], ExportSupport[Textbook], ImportSupport[Textbook] {

  def innerIndex(): View = {
    forward()
  }

  @response
  def downloadTemplate(): Any = {
    val presses = entityDao.search(OqlBuilder.from(classOf[Press], "p").orderBy("p.name")).map(x => x.code + " " + x.name)
    val categories = entityDao.search(OqlBuilder.from(classOf[BookCategory], "bc").orderBy("bc.name")).map(x => x.code + " " + x.name)
    val bookTypes = entityDao.search(OqlBuilder.from(classOf[BookType], "bt").orderBy("bt.name")).map(x => x.code + " " + x.name)
    val awardTypes = entityDao.search(OqlBuilder.from(classOf[BookAwardType], "bat").orderBy("bat.name")).map(x => x.code + " " + x.name)

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
    sheet.add("出版年月日", "textbook.publishedOn").date().required()
    sheet.add("是否自编", "textbook.madeInSchool").bool()
    sheet.add("教材类型", "textbook.bookType.code").ref(bookTypes)
    sheet.add("图书分类", "textbook.category.code").ref(categories)
    sheet.add("获奖级别", "textbook.awardType.code").ref(awardTypes)
    sheet.add("颁奖单位", "textbook.awardOrg").length(50)
    sheet.add("丛书", "textbook.series").length(100)
    sheet.add("价格", "textbook.price").decimal()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "教材模板.xlsx")
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
    if null == book.beginOn then book.beginOn = LocalDate.now
    if (entityDao.duplicate(classOf[Textbook], book.id, Map("isbn" -> book.isbn))) {
      addError("ISBN 重复")
      put("textbook", book)
      editSetting(book)
      forward("form")
    } else {
      super.saveAndRedirect(book)
    }
  }

  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val textbook = entityDao.get(classOf[Textbook], id.toLong)
    val cq = OqlBuilder.from(classOf[Course], "c")
    cq.where(":book in elements(c.textbooks)", textbook)
    val courses = entityDao.search(cq)
    put("textbook", textbook)
    put("courses", entityDao.search(cq))
    forward()
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TextbookImportListener(getProject, entityDao))
  }
}
