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
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.ems.app.Ems
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.openurp.base.edu.model.TeachingOffice
import org.openurp.base.model.{Department, Project, User}
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.TeachingOfficeImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

class TeachingOfficeAction extends ProjectRestfulAction[TeachingOffice], ExportSupport[TeachingOffice], ImportSupport[TeachingOffice] {

  @response
  def downloadTemplate(): Any = {
    val project = getProject
    val departs = project.departments.map(x => x.code + " " + x.name).toSeq.sorted

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("教研室信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("教研室代码", "teachingOffice.code").length(10).required().remark("≤10位")
    sheet.add("教研室名称", "teachingOffice.name").length(100).required()
    sheet.add("院系代码", "teachingOffice.department.code").ref(departs).required()
    sheet.add("负责人工号", "director.code")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "教研室模板.xlsx")
  }

  protected override def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
  }

  protected override def editSetting(g: TeachingOffice): Unit = {
    given project: Project = getProject

    if !g.persisted then g.beginOn = LocalDate.now
    put("departments", findInSchool(classOf[Department]))
    put("project", getProject)
    put("urp", Ems)
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fk = new ForeignerListener(entityDao)
    fk.addForeigerKey("code")
    setting.listeners = List(fk, new TeachingOfficeImportListener(getProject, entityDao))
  }

}
