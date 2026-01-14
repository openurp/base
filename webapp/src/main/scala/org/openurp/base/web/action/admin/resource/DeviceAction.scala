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
import org.beangle.transfer.importer.ImportSetting
import org.beangle.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.model.Project
import org.openurp.base.resource.model.{Classroom, Device}
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{ClassroomImportListener, DeviceImportListener}
import org.openurp.code.asset.model.DeviceType

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}

class DeviceAction extends ProjectRestfulAction[Device], ExportSupport[Device], ImportSupport[Device] {

  protected override def indexSetting(): Unit = {
    given project: Project = getProject

    put("deviceTypes", getCodes(classOf[DeviceType]))
  }

  override def getQueryBuilder: OqlBuilder[Device] = {
    val builder = super.getQueryBuilder
    builder.where("device.school=:school", getProject.school)
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  override protected def saveAndRedirect(device: Device): View = {
    device.updatedAt = Instant.now
    val project = getProject
    device.school = project.school
    super.saveAndRedirect(device)
  }

  override def editSetting(device: Device) = {
    given project: Project = getProject

    if (null == device.school) {
      device.school = project.school
    }
    if (!device.persisted) {
      device.beginOn = LocalDate.now()
    }
    put("deviceTypes", getCodes(classOf[DeviceType]))
    put("rooms", findInSchool(classOf[Classroom]))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val deviceTypes = codeService.get(classOf[DeviceType]).map(x => x.code + " " + x.name)
    val rooms = findInSchool(classOf[Classroom]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
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
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "设备信息模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new DeviceImportListener(entityDao, getProject))
  }
}
