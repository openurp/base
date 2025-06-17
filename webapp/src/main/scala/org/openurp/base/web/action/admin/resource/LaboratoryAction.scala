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

package org.openurp.base.web.action.admin.resource

import org.beangle.commons.activation.MediaTypes
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.event.bus.DataEvent
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.base.model.{Campus, Department, Project}
import org.openurp.base.resource.model.{Building, Laboratory}
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.LaboratoryImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}

/** 实验室管理
 */
class LaboratoryAction extends ProjectRestfulAction[Laboratory], ExportSupport[Laboratory], ImportSupport[Laboratory] {

  protected override def indexSetting(): Unit = {
    given project: Project = getProject
  }

  override def getQueryBuilder: OqlBuilder[Laboratory] = {
    val builder = super.getQueryBuilder
    builder.where("laboratory.school=:school", getProject.school)
    QueryHelper.addActive(builder, getBoolean("active"))
    getBoolean("virtual") foreach { virtual =>
      builder.where(if (virtual) "laboratory.roomNo is null" else "laboratory.roomNo is not null")
    }
    builder
  }

  override protected def saveAndRedirect(lab: Laboratory): View = {
    lab.updatedAt = Instant.now
    if (null == lab.beginOn) lab.beginOn = LocalDate.now()

    val departIds = getAll("departId2nd", classOf[Int])
    val newDeparts = entityDao.find(classOf[Department], departIds)
    val projectIds = getAll("roomProjectId", classOf[Int])
    val newProjects = entityDao.find(classOf[Project], projectIds)

    val project = getProject
    lab.school = project.school
    if (lab.persisted) {
      databus.publish(DataEvent.update(lab))
    }
    super.saveAndRedirect(lab)
  }

  override def editSetting(laboratory: Laboratory): Unit = {
    given project: Project = getProject

    if (null == laboratory.school) {
      laboratory.school = project.school
    }
    if (!laboratory.persisted) {
      laboratory.beginOn = LocalDate.now()
    }
    put("campuses", findInSchool(classOf[Campus]))
    put("buildings", findInSchool(classOf[Building]))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val campuses = findInSchool(classOf[Campus]).map(x => x.code + " " + x.name)
    val buildings = findInSchool(classOf[Building]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("实验室信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("实验室代码", "laboratory.code").length(10).required().remark("≤10位")
    sheet.add("实验室名称", "laboratory.name").length(100).required()
    sheet.add("房间号", "laboratory.roomNo").length(300)
    sheet.add("校区", "laboratory.campus.code").ref(campuses).required()
    sheet.add("教学楼", "laboratory.building.code").ref(buildings).required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "实验室模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new LaboratoryImportListener(entityDao, getProject))
  }
}
