/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.edu.admin

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.transfer.excel.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.api.annotation.response
import org.beangle.webmvc.api.view.{Stream, View}
import org.openurp.base.model.Department
import org.openurp.code.edu.model.{AcademicLevel, ExamMode, GradingMode}
import org.openurp.base.edu.code.model.{CourseCategory, CourseHourType, CourseType}
import org.openurp.base.edu.model.{Course, CourseHour, TeachingGroup}
import org.openurp.base.edu.web.helper.{CourseImportListener, QueryHelper}

class CourseAction extends ProjectRestfulAction[Course] {

  protected override def indexSetting(): Unit = {
    put("courseTypes", getCodes(classOf[CourseType]))
    put("courseCategories", getCodes(classOf[CourseCategory]))
    val departments = findInSchool(classOf[Department])
    put("departments", departments)
    put("teachingGroups", entityDao.getAll(classOf[TeachingGroup])) //FIXME for teachingGroup missing project
  }

  override def getQueryBuilder: OqlBuilder[Course] = {
    val builder: OqlBuilder[Course] = OqlBuilder.from(entityName, simpleEntityName)
    getDate("beginOn").foreach(beginOn => {
      getDate("endOn").foreach(endOn => {
        builder.where("course.beginOn between :beginOn and :endOn", beginOn, endOn)
      })
    })
    populateConditions(builder)
    builder.where(simpleEntityName + ".project = :project", getProject)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    QueryHelper.addTemporalOn(builder, getBoolean("active"))
  }

  override def editSetting(entity: Course) = {
    put("courseTypes", getCodes(classOf[CourseType]))
    put("examModes", getCodes(classOf[ExamMode]))
    put("gradingModes", getCodes(classOf[GradingMode]))
    put("courseCategories", getCodes(classOf[CourseCategory]))
    put("departments", findInSchool(classOf[Department]))
    put("teachingGroups", entityDao.getAll(classOf[TeachingGroup])) //FIXME for teachingGroup missing project

    var levels = getProject.levels.map(_.toLevel).toSet.toBuffer
    levels --= entity.levels
    put("levels", levels)
    put("hourTypes", getCodes(classOf[CourseHourType]))
    if (!entity.persisted) {
      entity.project = getProject
      entity.beginOn = LocalDate.now
      entity.levels ++= levels
      levels.clear()
    }
    super.editSetting(entity)
  }

  protected override def saveAndRedirect(entity: Course): View = {
    val course = entity.asInstanceOf[Course]

    val hourTypes = getCodes(classOf[CourseHourType])
    hourTypes foreach { ht =>
      val creditHour = getInt("creditHour" + ht.id)
      val week = getInt("week" + ht.id)
      course.hours find (h => h.hourType == ht) match {
        case Some(hour) =>
          if (week.isEmpty && creditHour.isEmpty) {
            course.hours -= hour
          } else {
            hour.weeks = week.getOrElse(0)
            hour.creditHours = creditHour.getOrElse(0)
          }
        case None =>
          if (!(week.isEmpty && creditHour.isEmpty)) {
            val newHour = new CourseHour()
            newHour.course = course
            newHour.hourType = ht
            newHour.weeks = week.getOrElse(0)
            newHour.creditHours = creditHour.getOrElse(0)
            course.hours += newHour
          }
      }
    }
    val orphan = course.hours.filter(x => !hourTypes.contains(x.hourType))
    course.hours --= orphan
    val levelIds = getAll("levelId2nd", classOf[Int])
    val newLevels = entityDao.find(classOf[AcademicLevel], levelIds)
    val removed = course.levels filter { x => !newLevels.contains(x) }
    course.levels.subtractAll(removed)
    newLevels foreach { l =>
      if (!course.levels.contains(l))
        course.levels += l
    }

    course.gradingModes.clear()
    val gradingModeIds = getAll("gradingModeId2nd", classOf[Int])
    course.gradingModes ++= entityDao.find(classOf[GradingMode], gradingModeIds)

    super.saveAndRedirect(entity)
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new CourseImportListener(entityDao, getProject))
  }

  def newCourses(): View = {
    getDate("beginOn").foreach(beginOn => {
      getDate("endOn").foreach(endOn => {
        put("courses", entityDao.search(getQueryBuilder))
      })
    })
    forward()
  }

  @response
  def downloadTemplate(): Any = {
    val courseTypes = entityDao.search(OqlBuilder.from(classOf[CourseType], "p").orderBy("p.name")).map(_.name)
    val examModes = entityDao.search(OqlBuilder.from(classOf[ExamMode], "bc").orderBy("bc.name")).map(_.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.name")).map(_.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("课程信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("课程代码", "course.code").length(10).required().remark("≤10位")
    sheet.add("课程名称", "course.name").length(100).required()
    sheet.add("课程英文名称", "course.enName").length(300)
    sheet.add("课程类别", "course.courseType.name").ref(courseTypes).required()
    sheet.add("开课院系", "course.department.name").ref(departs).required()
    sheet.add("学分", "course.credits").required().decimal()
    sheet.add("总课时", "course.creditHours").required().decimal()
    sheet.add("周课时", "course.weekHours").required().decimal()
    sheet.add("考核方式名称", "course.examMode.name").ref(examModes).required()
    sheet.add("是否实践课", "course.practical").bool()
    sheet.add("是否设置补考", "course.hasMakeup").bool()
    sheet.add("是否计算绩点", "course.calgp").bool()

    val code = schema.createScheet("数据字典")
    code.add("课程类别").data(courseTypes)
    code.add("考核方式").data(examModes)
    code.add("开课院系").data(departs)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "课程模板.xlsx")
  }
}
