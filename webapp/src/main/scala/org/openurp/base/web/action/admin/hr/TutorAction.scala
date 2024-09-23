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
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.{mapping, param, response}
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.hr.model.{Staff, TutorMajor}
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{TutorImportListener, UrpUserHelper}
import org.openurp.code.hr.model.WorkStatus
import org.openurp.code.job.model.{ProfessionalTitle, TutorType}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

/** 导师管理
 */
class TutorAction extends ProjectRestfulAction[Staff], ExportSupport[Staff], ImportSupport[Staff] {
  var urpUserHelper: UrpUserHelper = _

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("tutorTypes", codeService.get(classOf[TutorType]))
    put("departments", findInSchool(classOf[Department]))
    put("statuses", codeService.get(classOf[WorkStatus]))
    put("titles", codeService.get(classOf[ProfessionalTitle]))
  }

  override def getQueryBuilder: OqlBuilder[Staff] = {
    val query = super.getQueryBuilder
    query.where("staff.tutorType is not null")
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(tutor: Staff) = {
    given project: Project = getProject

    put("tutorTypes", codeService.get(classOf[TutorType]))
    if (!tutor.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("s.tutorType is null")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
      tutor.beginOn = LocalDate.now
    }
    super.editSetting(tutor)
  }

  override protected def saveAndRedirect(staff: Staff): View = {
    val project = getProject
    entityDao.saveOrUpdate(staff)
    redirect("search", "info.save.success")
  }

  /** 消除导师属性
   * @param entities
   * @return
   */
  override protected def removeAndRedirect(entities: Seq[Staff]): View = {
    entities foreach { s => s.tutorType = None }
    entityDao.saveOrUpdate(entities)
    redirect("search", "info.remvoe.success")
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val tutorTypes = codeService.get(classOf[TutorType]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("导师信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    if tutorTypes.nonEmpty then sheet.add("导师类别", "staff.tutorType.code").ref(tutorTypes)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "导师信息.xlsx")
  }

  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val staff = entityDao.get(classOf[Staff], id.toLong)
    val majors = entityDao.findBy(classOf[TutorMajor], "staff", staff)
    put("staff", staff)
    put("majors", majors)
    forward()
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TutorImportListener(entityDao, getProject))
  }
}
