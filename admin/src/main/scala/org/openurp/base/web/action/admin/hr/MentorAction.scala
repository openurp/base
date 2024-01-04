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

import org.beangle.commons.activation.{MediaType, MediaTypes}
import org.beangle.data.dao.{Operation, OqlBuilder}
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.ImportSupport
import org.openurp.base.hr.model.{Mentor, Staff}
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{MentorImportListener, QueryHelper, UrpUserHelper}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class MentorAction extends ProjectRestfulAction[Mentor], ImportSupport[Mentor] {

  var urpUserHelper: UrpUserHelper = _

  override def getQueryBuilder: OqlBuilder[Mentor] = {
    QueryHelper.addTemporalOn(super.getQueryBuilder, getBoolean("active"))
  }

  override def editSetting(mentor: Mentor): Unit = {
    given project: Project = getProject

    if (!mentor.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("not exists(from " + classOf[Mentor].getName + " t where t.staff=s)")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
    }
    super.editSetting(mentor)
  }

  override protected def saveAndRedirect(mentor: Mentor): View = {
    val p = getProject
    val staff = entityDao.get(classOf[Staff], mentor.staff.id)
    if (!mentor.persisted) {
      mentor.id = mentor.staff.id
    }
    mentor.projects += p
    mentor.name = staff.name
    entityDao.saveOrUpdate(mentor)
    redirect("search", "info.save.success")
  }

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("辅导员信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("起始日期", "mentor.beginOn").date().required()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "辅导员信息.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new MentorImportListener(entityDao, getProject, urpUserHelper))
  }
}
