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

package org.openurp.base.ws.admin.resource

import org.beangle.commons.activation.MediaTypes
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.she.webmvc.{ExportSupport, QueryHelper}
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.view.Stream
import org.openurp.base.model.{Campus, Department, Project}
import org.openurp.base.resource.model.{Building, Classroom, Device, Laboratory}
import org.openurp.base.ws.admin.{JsonImportSupport, ProjectAdminJsonWS, SchoolAdminJsonWS}
import org.openurp.code.asset.model.{ClassroomType, DeviceType}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}

/** /api/base/admin/resource/buildings */
class BuildingWS extends SchoolAdminJsonWS[Building] {

  override protected def getQueryBuilder: OqlBuilder[Building] = {
    val builder = schoolScoped("building")
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  override protected def prepare(building: Building): Unit = {
    building.school = getSchool
    if (!building.persisted && null == building.beginOn) building.beginOn = LocalDate.now
  }
}

/** /api/base/admin/resource/classrooms */
class ClassroomWS extends ProjectAdminJsonWS[Classroom], ExportSupport[Classroom], JsonImportSupport[Classroom] {

  override def getQueryBuilder: OqlBuilder[Classroom] = {
    val builder = super.getQueryBuilder
    val project = getProject
    builder.where("classroom.school=:school", project.school)
    builder.where(":project in elements(classroom.projects)", project)
    QueryHelper.addActive(builder, getBoolean("active"))
    getBoolean("virtual") foreach { virtual =>
      builder.where(if (virtual) "classroom.roomNo is null" else "classroom.roomNo is not null")
    }
    builder
  }

  override protected def prepare(room: Classroom): Unit = {
    if (null == room.beginOn) room.beginOn = LocalDate.now
    val project = getProject
    room.school = project.school
    if (formSubmitted("classroom.code") || formSubmitted("departId2nd") || formSubmitted("roomProjectId")) {
      val departIds = getAll("departId2nd", classOf[Int])
      val newDeparts = entityDao.find(classOf[Department], departIds)
      val removed = room.departs filter { x => !newDeparts.contains(x) }
      room.departs.subtractAll(removed)
      newDeparts foreach { l =>
        if (!room.departs.contains(l)) room.departs += l
      }
      val projectIds = getAll("roomProjectId", classOf[Int])
      val newProjects = entityDao.find(classOf[org.openurp.base.model.Project], projectIds)
      room.projects.clear()
      room.projects.addAll(newProjects)
    }
    if (!room.projects.contains(project)) room.projects.addOne(project)
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    given project: org.openurp.base.model.Project = getProject
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    fl.addScope(classOf[org.openurp.base.model.Campus], Map("school" -> project.school))
    fl.addScope(classOf[Building], Map("school" -> project.school))
    setting.listeners = List(fl, new org.openurp.base.web.helper.ClassroomImportListener(entityDao, getProject))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val classroomTypes = codeService.get(classOf[ClassroomType]).map(x => x.code + " " + x.name)
    val campuses = findInSchool(classOf[Campus]).map(x => x.code + " " + x.name)
    val buildings = findInSchool(classOf[Building]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("教室信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("教室代码", "classroom.code").length(10).required().remark("≤10位")
    sheet.add("教室名称", "classroom.name").length(100).required()
    sheet.add("教室英文名称", "classroom.enName").length(300)
    sheet.add("房间号", "classroom.roomNo").length(300)
    sheet.add("校区", "classroom.campus.code").ref(campuses).required()
    sheet.add("教学楼", "classroom.building.code").ref(buildings).required()
    sheet.add("教室类型", "classroom.roomType.code").ref(classroomTypes).required()
    sheet.add("总容量", "classroom.capacity").required().decimal()
    sheet.add("听课容量", "classroom.courseCapacity").required().decimal()
    sheet.add("考试容量", "classroom.examCapacity").required().decimal()
    sheet.add("使用部门", "departNames")
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教室模板.xlsx")
  }
}

/** /api/base/admin/resource/laboratories */
class LaboratoryWS extends ProjectAdminJsonWS[Laboratory], ExportSupport[Laboratory], JsonImportSupport[Laboratory] {

  override def getQueryBuilder: OqlBuilder[Laboratory] = {
    val builder = super.getQueryBuilder
    builder.where("laboratory.school=:school", getProject.school)
    QueryHelper.addActive(builder, getBoolean("active"))
    getBoolean("virtual") foreach { virtual =>
      builder.where(if (virtual) "laboratory.room is null" else "laboratory.room is not null")
    }
    builder
  }

  override protected def prepare(lab: Laboratory): Unit = {
    if (null == lab.beginOn) lab.beginOn = LocalDate.now
    lab.school = getProject.school
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new org.openurp.base.web.helper.LaboratoryImportListener(entityDao, getProject))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val rooms = findInSchool(classOf[Classroom]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("实验室信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("实验室代码", "laboratory.code").length(10).required().remark("≤10位")
    sheet.add("实验室名称", "laboratory.name").length(100).required()
    sheet.add("对应教室", "room.code").ref(rooms)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "实验室模板.xlsx")
  }
}

/** /api/base/admin/resource/devices */
class DeviceWS extends ProjectAdminJsonWS[Device], ExportSupport[Device], JsonImportSupport[Device] {

  override def getQueryBuilder: OqlBuilder[Device] = {
    val builder = super.getQueryBuilder
    builder.where("device.school=:school", getProject.school)
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  override protected def prepare(device: Device): Unit = {
    device.updatedAt = Instant.now
    device.school = getProject.school
    if (!device.persisted && null == device.beginOn) device.beginOn = LocalDate.now
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new org.openurp.base.web.helper.DeviceImportListener(entityDao, getProject))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val deviceTypes = codeService.get(classOf[DeviceType]).map(x => x.code + " " + x.name)
    val rooms = findInSchool(classOf[Classroom]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("设备信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("设备代码", "device.code").length(10).required().remark("≤10位")
    sheet.add("设备名称", "device.name").length(100).required()
    sheet.add("设备类型", "device.deviceType.code").ref(deviceTypes).required()
    sheet.add("教室", "device.room.code").ref(rooms).required()
    sheet.add("UUID", "device.uuid")
    sheet.add("IP", "device.ip")
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "设备信息模板.xlsx")
  }
}
