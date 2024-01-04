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
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.ImportSupport
import org.openurp.base.hr.model.{Official, Staff}
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{OfficialImportListener, QueryHelper}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

/** 教学秘书
 */
class OfficialAction extends ProjectRestfulAction[Official], ImportSupport[Official] {

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", entityDao.findBy(classOf[Department], "school", project.school))
    super.indexSetting()
  }

  override def getQueryBuilder: OqlBuilder[Official] = {
    QueryHelper.addTemporalOn(super.getQueryBuilder, getBoolean("active"))
  }

  override def editSetting(Official: Official) = {
    given project: Project = getProject

    put("departments", entityDao.findBy(classOf[Department], "school", project.school))
    if (!Official.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
    }
    super.editSetting(Official)
  }

  override protected def saveAndRedirect(official: Official): View = {
    if (official.department == null) {
      official.department = entityDao.get(classOf[Staff], official.staff.id).department
    }
    super.saveAndRedirect(official)
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.name")).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("领导干部信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("部门", "official.department.code").ref(departs).remark("默认为所在部门")
    sheet.add("行政职务", "official.duty").length(300).required()
    sheet.add("是否兼任", "official.parttime").bool()
    sheet.add("起始日期", "official.beginOn").date()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "领导干部信息.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    setting.listeners = List(fl, new OfficialImportListener(entityDao, getProject.school))
  }
}
