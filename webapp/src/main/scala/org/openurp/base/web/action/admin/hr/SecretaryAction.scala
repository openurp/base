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
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.ImportSupport
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.hr.model.{Secretary, Staff}
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{SecretaryImportListener, UrpUserHelper}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

/** 教学秘书
 */
class SecretaryAction extends ProjectRestfulAction[Secretary], ImportSupport[Secretary] {

  var urpUserHelper: UrpUserHelper = _

  override def getQueryBuilder: OqlBuilder[Secretary] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(secretary: Secretary) = {
    given project: Project = getProject

    if (!secretary.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("not exists(from " + classOf[Secretary].getName + " t where t.staff=s)")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
      secretary.beginOn = LocalDate.now
    }
    super.editSetting(secretary)
  }

  override protected def saveAndRedirect(s: Secretary): View = {
    val p = getProject
    val staff = entityDao.get(classOf[Staff], s.staff.id)
    s.projects += p
    entityDao.saveOrUpdate(s)
    urpUserHelper.createUser(staff, None)
    redirect("search", "info.save.success")
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("辅导员信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("办公电话", "secretary.officePhone").length(10).remark("≤13位")
    sheet.add("办公室地址", "secretary.officeAddr").length(10)
    sheet.add("办公邮箱", "secretary.officeEmail").length(10)
    sheet.add("起始日期", "secretary.beginOn").date().remark("默认从今天开始")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "教学秘书信息.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new SecretaryImportListener(entityDao, getProject, urpUserHelper))
  }
}
