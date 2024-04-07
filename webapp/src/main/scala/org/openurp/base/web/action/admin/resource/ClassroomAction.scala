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
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.model.{Campus, Department, Project}
import org.openurp.base.resource.model.{Building, Classroom}
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.ClassroomImportListener
import org.openurp.code.edu.model.ClassroomType

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}

class ClassroomAction extends ProjectRestfulAction[Classroom], ExportSupport[Classroom], ImportSupport[Classroom] {

  protected override def indexSetting(): Unit = {
    given project: Project = getProject

    put("roomTypes", getCodes(classOf[ClassroomType]))
    put("campuses", findInSchool(classOf[Campus]))
  }

  override def getQueryBuilder: OqlBuilder[Classroom] = {
    val builder = super.getQueryBuilder
    builder.where("classroom.school=:school", getProject.school)
    QueryHelper.addActive(builder, getBoolean("active"))
    getBoolean("virtual") foreach { virtual =>
      builder.where(if (virtual) "classroom.roomNo is null" else "classroom.roomNo is not null")
    }
    builder
  }

  override protected def saveAndRedirect(entity: Classroom): View = {
    entity.updatedAt = Instant.now
    if (null == entity.beginOn) entity.beginOn = LocalDate.now()

    val departIds = getAll("departId2nd", classOf[Int])
    val newDeparts = entityDao.find(classOf[Department], departIds)
    val removed = entity.departs filter { x => !newDeparts.contains(x) }
    entity.departs.subtractAll(removed)
    newDeparts foreach { l =>
      if (!entity.departs.contains(l))
        entity.departs += l
    }
    val projectIds = getAll("roomProjectId", classOf[Int])
    val newProjects = entityDao.find(classOf[Project], projectIds)
    entity.projects.clear()
    entity.projects.addAll(newProjects)

    val project = getProject
    entity.school = project.school
    if (!entity.projects.contains(project)) entity.projects.addOne(project)
    super.saveAndRedirect(entity)
  }

  override def editSetting(classroom: Classroom) = {
    given project: Project = getProject

    if (null == classroom.school) {
      classroom.school = project.school
    }
    if (!classroom.persisted) {
      classroom.beginOn = LocalDate.now()
    }
    put("roomTypes", getCodes(classOf[ClassroomType]))
    put("campuses", findInSchool(classOf[Campus]))
    put("buildings", findInSchool(classOf[Building]))
    val departs = Collections.newBuffer[Department]
    departs ++= project.departments
    departs --= classroom.departs
    put("projects", entityDao.findBy(classOf[Project], "school", project.school))
    put("departs", departs)
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val classroomTypes = codeService.get(classOf[ClassroomType]).map(x => x.code + " " + x.name)
    val campuses = findInSchool(classOf[Campus]).map(x => x.code + " " + x.name)
    val buildings = findInSchool(classOf[Building]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
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
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "教室模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new ClassroomImportListener(entityDao, getProject))
  }
}
