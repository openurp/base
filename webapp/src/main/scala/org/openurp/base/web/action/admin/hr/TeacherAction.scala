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
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{Operation, OqlBuilder}
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.response
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.{Course, TeachingOffice}
import org.openurp.base.hr.model.{Staff, Teacher}
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{TeacherImportListener, UrpUserHelper}
import org.openurp.code.edu.model.{Degree, DegreeLevel, EducationDegree}
import org.openurp.code.hr.model.WorkStatus
import org.openurp.code.job.model.{ProfessionalTitle, TutorType}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}

class TeacherAction extends ProjectRestfulAction[Teacher], ExportSupport[Teacher], ImportSupport[Teacher] {
  var urpUserHelper: UrpUserHelper = _

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("tutorTypes", codeService.get(classOf[TutorType]))
    put("departments", findInSchool(classOf[Department]))
    put("teachingOffices", findInProject(classOf[TeachingOffice]))
    put("statuses", codeService.get(classOf[WorkStatus]))
    put("titles", codeService.get(classOf[ProfessionalTitle]))
  }

  override def getQueryBuilder: OqlBuilder[Teacher] = {
    put("tutorTypes", codeService.get(classOf[TutorType]))
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(teacher: Teacher) = {
    given project: Project = getProject

    put("tutorTypes", codeService.get(classOf[TutorType]))
    put("departments", findInSchool(classOf[Department]))
    put("campuses", findInSchool(classOf[Campus]))
    if (!teacher.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("not exists(from " + classOf[Teacher].getName + " t where t.staff=s)")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
      teacher.beginOn = LocalDate.now
    }
    put("teachingOffices", findInProject(classOf[TeachingOffice]))
    put("projects", entityDao.findBy(classOf[Project], "school", project.school))
    super.editSetting(teacher)
  }

  def activate(): View = {
    val teachers = entityDao.find(classOf[Teacher], getLongIds("teacher"))
    val isActivate = getBoolean("isActivate", true)
    if (isActivate) {
      teachers.foreach { c => c.endOn = None }
    } else {
      val yesterday = LocalDate.now.minusDays(1)
      teachers.foreach { c => c.endOn = Some(yesterday) }
    }
    entityDao.saveOrUpdate(teachers)
    redirect("search", "操作成功")
  }

  override protected def saveAndRedirect(teacher: Teacher): View = {
    val project = getProject
    val projectIds = getAll("teacherProjectId", classOf[Int])
    val newProjects = entityDao.find(classOf[Project], projectIds)
    teacher.projects.clear()
    teacher.projects.addAll(newProjects)
    if (!teacher.projects.contains(project)) teacher.projects.addOne(project)

    val campusIds = getIntIds("campus")
    val campuses = if campusIds.isEmpty then List.empty[Campus] else entityDao.find(classOf[Campus], campusIds)
    teacher.campuses.filter(campuses.contains)
    teacher.campuses.addAll(campuses)

    if (!teacher.persisted) {
      teacher.id = teacher.staff.id //assigned id
    }
    val staff = entityDao.get(classOf[Staff], teacher.staff.id)
    teacher.name = staff.name
    teacher.user = entityDao.findBy(classOf[User], "school" -> staff.school, "code" -> staff.code).head
    entityDao.saveOrUpdate(teacher)
    urpUserHelper.createTeacherUser(teacher)
    redirect("search", "info.save.success")
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.code")).map(x => x.code + " " + x.name)
    val tutorTypes = codeService.get(classOf[TutorType]).map(x => x.code + " " + x.name)
    val teachingOffices = findInProject(classOf[TeachingOffice]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("教师信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("教师工号", "teacher.staff.code").length(10).required().remark("≤10位")
    sheet.add("教学所在部门", "teacher.department.code").ref(departs).required()
    if tutorTypes.nonEmpty then sheet.add("导师类别", "teacher.tutorType.code").ref(tutorTypes)
    if teachingOffices.nonEmpty then sheet.add("所在教研室", "teacher.office.code").ref(teachingOffices)
    sheet.add("任教起始日期", "teacher.beginOn").date()
    sheet.add("教师资格证号码", "teacher.tqcNumber").length(20)
    sheet.add("其他职业资格证书和等级说明", "teacher.oqc").length(100)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "教师信息.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TeacherImportListener(entityDao, getProject))
  }
}
