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

package org.openurp.base.web.action.admin.edu

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.collection.{Collections, Order}
import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.lang.Charsets
import org.beangle.commons.net.http.HttpUtils
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.ems.app.Ems
import org.beangle.event.bus.DataEvent
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.api.URPTool
import org.openurp.base.edu.model.{Course, CourseHour, CourseJournal, CourseLevel}
import org.openurp.base.model.{Department, Project}
import org.openurp.base.service.Features
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.CourseImportListener
import org.openurp.code.edu.model.*

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.net.URLEncoder
import java.time.{Instant, LocalDate}

class CourseAction extends ProjectRestfulAction[Course], ExportSupport[Course], ImportSupport[Course] {

  protected override def indexSetting(): Unit = {
    given project: Project = getProject

    put("courseTypes", getCodes(classOf[CourseType]))
    put("categories", getCodes(classOf[CourseCategory]))
    val departments = findInSchool(classOf[Department])
    put("departments", departments)
    put("courseNatures", getCodes(classOf[CourseNature]))
  }

  protected override def editSetting(c: Course): Unit = {
    given project: Project = getProject

    put("courseTypes", getCodes(classOf[CourseType]))
    put("examModes", getCodes(classOf[ExamMode]))
    put("gradingModes", getCodes(classOf[GradingMode]))
    val categories = getCodes(classOf[CourseCategory])
    put("categories", categories.groupBy(_.dimension))

    put("departments", findInSchool(classOf[Department]))

    val levels = project.levels
    put("courseNatures", getCodes(classOf[CourseNature]))
    put("courseModules", getCodes(classOf[CourseModule]))
    put("courseRanks", getCodes(classOf[CourseRank]))
    put("teachingNatures", getCodes(classOf[TeachingNature]))
    if (!c.persisted) {
      c.project = project
      c.beginOn = LocalDate.now
      c.calgp = true
      c.hasMakeup = true
      levels foreach { l =>
        val cl = new CourseLevel
        cl.course = c
        cl.level = l
        c.levels += cl
      }
    }
    put("courseLevels", c.levels.map(_.level))
    put("levels", project.levels)
    put("levelCreditSupported", getConfig(Features.Edu.CourseLevelCreditSupported))
    put("hoursPerCredit", getConfig(Features.Edu.CourseHoursPerCredit))

    put("courseCategories", c.categories.map(x => (x.dimension, x)).toMap)
    put("project", project)
    put("api", Ems.api)
    super.editSetting(c)
  }

  def activate(): View = {
    val courses = entityDao.find(classOf[Course], getLongIds("course"))
    val isActivate = getBoolean("isActivate", true)
    if (isActivate) {
      courses.foreach { c => c.endOn = None; c.updatedAt = Instant.now }
    } else {
      val yesterday = LocalDate.now.minusDays(1)
      courses.foreach { c => c.endOn = Some(yesterday); c.updatedAt = Instant.now }
    }
    entityDao.saveOrUpdate(courses)
    redirect("search", "操作成功")
  }

  def newCourses(): View = {
    getDate("beginOn").foreach(beginOn => {
      getDate("endOn").foreach(endOn => {
        put("courses", entityDao.search(getQueryBuilder))
      })
    })
    forward()
  }

  override def getQueryBuilder: OqlBuilder[Course] = {
    val builder = OqlBuilder.from(classOf[Course], simpleEntityName)
    getDate("beginOn").foreach(beginOn => {
      getDate("endOn").foreach(endOn => {
        builder.where("course.beginOn between :beginOn and :endOn", beginOn, endOn)
      })
    })
    getInt("category.id") foreach { categoryId =>
      builder.where("exists(from course.categories as cg where cg.id=:categoryId)", categoryId)
    }
    populateConditions(builder)
    builder.where(simpleEntityName + ".project = :project", getProject)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  @response
  def downloadTemplate(): Any = {
    val school = getProject.school
    val courseTypes = codeService.get(classOf[CourseType]).map(x => x.code + " " + x.name)
    val examModes = codeService.get(classOf[ExamMode]).map(x => x.code + " " + x.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").where("bt.school=:school", school)
      .orderBy("bt.name")).map(x => x.code + " " + x.name)
    val natures = codeService.get(classOf[CourseNature]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("课程信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("课程代码", "course.code").length(10).required().remark("≤10位")
    sheet.add("课程名称", "course.name").length(100).required()
    sheet.add("课程英文名称", "course.enName").length(300)
    sheet.add("课程类别", "course.courseType.code").ref(courseTypes).required()
    sheet.add("开课院系", "course.department.code").ref(departs).required()
    sheet.add("学分", "course.defaultCredits").required().decimal()
    sheet.add("总课时", "course.creditHours").required().decimal()
    sheet.add("周课时", "course.weekHours").required().decimal()
    sheet.add("实践周数", "course.weeks").decimal()
    sheet.add("考核方式名称", "course.examMode.code").ref(examModes).required()
    sheet.add("课程性质", "course.nature.code").ref(natures).required()
    //sheet.add("评教分类", "course.category.code").ref(categories)
    sheet.add("是否设置补考", "course.hasMakeup").bool()
    sheet.add("是否计算绩点", "course.calgp").bool()
    sheet.add("设立日期", "course.beginOn").date().required()
    sheet.add("有效期至", "course.endOn").date()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "课程模板.xlsx")
  }

  protected override def saveAndRedirect(course: Course): View = {
    given project: Project = getProject

    val teachingNatures = getCodes(classOf[TeachingNature])
    teachingNatures foreach { ht =>
      val creditHour = getInt("creditHour" + ht.id)
      course.hours find (h => h.nature == ht) match {
        case Some(hour) =>
          if (creditHour.isEmpty) {
            course.hours -= hour
          } else {
            hour.creditHours = creditHour.getOrElse(0)
          }
        case None =>
          if (creditHour.nonEmpty) {
            val newHour = new CourseHour()
            newHour.course = course
            newHour.nature = ht
            newHour.creditHours = creditHour.getOrElse(0)
            course.hours += newHour
          }
      }
    }
    val orphan = course.hours.filter(x => !teachingNatures.contains(x.nature))
    course.hours --= orphan
    val levelIds = getAll("levelId", classOf[Int])
    val newLevels = entityDao.find(classOf[EducationLevel], levelIds)
    val removed = course.levels filter { x => !newLevels.contains(x.level) }
    course.levels.subtractAll(removed)
    newLevels foreach { l =>
      val courseLevel = course.levels.find(_.level == l) match {
        case None =>
          val cl = new CourseLevel(course, l)
          course.levels += cl
          cl
        case Some(cl) => cl
      }
      courseLevel.credits = getFloat(s"level${l.id}.credits")
    }

    val categoryIds = getAll("category.id", classOf[Int])
    course.categories.clear()
    course.categories.addAll(entityDao.find(classOf[CourseCategory], categoryIds))

    entityDao.saveOrUpdate(course)
    databus.publish(DataEvent.update(course))
    if (course.journals.isEmpty) {
      course.journals += new CourseJournal(course, course.beginOn)
      entityDao.saveOrUpdate(course)
    }
    super.saveAndRedirect(course)
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    val project = getProject
    fl.addScope(classOf[Department], Map("school" -> project.school))
    setting.listeners = List(fl, new CourseImportListener(entityDao, getProject))
  }

  /** 批量检查英文
   * @return
   */
  def checkEnName(): View = {
    val courses = entityDao.find(classOf[Course], getLongIds("course"))
    val messages = Collections.newMap[Course, String]
    courses foreach { course =>
      course.enName match {
        case None => messages.put(course, "缺少英文名")
        case Some(enName) =>
          val rs = Json.parse(HttpUtils.getText(Ems.api + "/tools/lang/en/check.json?name=" +
            URLEncoder.encode(enName, Charsets.UTF_8)).getText).asInstanceOf[JsonObject]
          if (!rs.getBoolean("success")) {
            messages.put(course, rs.getArray("data").map(_.toString).mkString(";"))
          }
      }
    }
    put("messages", messages)
    put("courses", messages.keys.toBuffer.sortBy(_.code))
    forward()
  }
}
