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

package org.openurp.base.web.action.admin.hr

import org.beangle.commons.activation.MediaTypes
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.she.webmvc.{ImportSupport, QueryHelper}
import org.beangle.transfer.importer.ImportSetting
import org.beangle.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.base.hr.model.{CadreAssignment, Staff}
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.CadreAssignmentImportListener
import org.openurp.code.hr.model.CadrePostRank

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

/** 教学秘书
 */
class CadreAssignmentAction extends ProjectRestfulAction[CadreAssignment], ImportSupport[CadreAssignment] {

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", entityDao.findBy(classOf[Department], "school", project.school))
    put("ranks", codeService.get(classOf[CadrePostRank]))
    super.indexSetting()
  }

  override def getQueryBuilder: OqlBuilder[CadreAssignment] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(official: CadreAssignment): Unit = {
    given project: Project = getProject

    put("departments", entityDao.findBy(classOf[Department], "school", project.school))
    if (!official.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
      official.beginOn = LocalDate.now
    }

    put("ranks", codeService.get(classOf[CadrePostRank]))
    super.editSetting(official)
  }

  override protected def saveAndRedirect(official: CadreAssignment): View = {
    if (official.department == null) {
      official.department = entityDao.get(classOf[Staff], official.staff.id).department
    }
    super.saveAndRedirect(official)
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.name")).map(x => x.code + " " + x.name)
    val ranks = codeService.get(classOf[CadrePostRank]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("领导干部信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("部门", "assignment.department.code").ref(departs).remark("默认为所在部门")
    sheet.add("职务级别", "assignment.rank.code").ref(ranks).required()
    sheet.add("行政职务", "assignment.post").length(300).required()
    sheet.add("是否兼任", "assignment.concurrent").bool()
    sheet.add("是否主职", "assignment.principal").bool()
    sheet.add("起始日期", "assignment.beginOn").date()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "领导干部信息任职.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    setting.listeners = List(fl, new CadreAssignmentImportListener(entityDao, getProject.school))
  }

  override protected def simpleEntityName: String = {
    "assignment"
  }
}
