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
import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.Stream
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.{CourseTextbook, Textbook}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{CourseImportListener, CourseTextbookImportListener}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class CourseTextbookAction extends ProjectRestfulAction[CourseTextbook], ExportSupport[CourseTextbook], ImportSupport[CourseTextbook] {

  override protected def editSetting(ctb: CourseTextbook): Unit = {
    given project: Project = getProject

    put("textbooks", entityDao.findBy(classOf[Textbook], "project", project))
    put("project", project)
    if (!ctb.persisted) {
      ctb.required = true
      ctb.recommended = true
    }
    super.editSetting(ctb)
  }

  override protected def getQueryBuilder: OqlBuilder[CourseTextbook] = {
    val builder = OqlBuilder.from(classOf[CourseTextbook], simpleEntityName)
    populateConditions(builder)
    val project = getProject
    builder.where("courseTextbook.course.project=:project", project)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    builder.tailOrder(simpleEntityName + ".id")
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  @response
  def downloadTemplate(): Any = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("课程教材信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("课程代码", "courseTextbook.course.code").length(10).required().remark("≤10位")
    sheet.add("教材ISBN", "courseTextbook.textbook.isbn").length(100).required()
    sheet.add("是否推荐教材", "courseTextbook.recommended").bool()
    sheet.add("是否必选教材", "courseTextbook.required").bool()
    sheet.add("设立日期", "courseTextbook.beginOn").date().required()
    sheet.add("有效期至", "courseTextbook.endOn").date()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "课程默认教材模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    setting.listeners = List(fl, new CourseTextbookImportListener(entityDao, getProject))
  }
}
