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

package org.openurp.base.ws.admin.edu

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.collection.Collections
import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.lang.{Charsets, Strings}
import org.beangle.commons.net.http.HttpUtils
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.ems.app.Ems
import org.beangle.event.bus.DataEvent
import org.beangle.she.webmvc.{ExportSupport, QueryHelper}
import org.beangle.webmvc.annotation.{mapping, response}
import org.beangle.webmvc.view.Stream
import org.openurp.base.ws.admin.JsonImportSupport
import org.openurp.base.edu.model.*
import org.openurp.base.model.{Department, Project}
import org.openurp.base.ws.admin.ProjectAdminJsonWS
import org.openurp.code.edu.model.{BookAwardType, BookType, CourseCategory, CourseNature, CourseType, DisciplineCategory, EducationLevel, ExamMode, TeachingNature}
import org.openurp.code.sin.model.{BookCategory, Press, PressGrade, TextbookForm}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate, YearMonth}

/** /api/base/admin/edu/majors */
class MajorWS extends ProjectAdminJsonWS[Major], ExportSupport[Major] {

  override def getQueryBuilder: OqlBuilder[Major] = {
    val query = super.getQueryBuilder
    getInt("department.id") foreach { d =>
      query.where("exists(from major.journals as mj where mj.depart.id=:departId)", d)
    }
    getInt("level.id") foreach { d =>
      query.where("exists(from major.journals as mj where mj.level.id=:levelId)", d)
    }
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(major: Major): Unit = {
    if (!major.persisted && null == major.beginOn) major.beginOn = LocalDate.now
    if (formSubmitted("category.id")) {
      val category = entityDao.get(classOf[DisciplineCategory], getIntId("category"))
      if (major.disciplines.nonEmpty) {
        major.disciplines.maxBy(_.beginOn).category = category
      } else {
        val md = new MajorDiscipline
        md.major = major
        md.category = category
        md.beginOn = major.beginOn
        major.disciplines.addOne(md)
      }
    }
  }
}

/** /api/base/admin/edu/courses */
class CourseWS extends ProjectAdminJsonWS[Course], ExportSupport[Course], JsonImportSupport[Course] {

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
    getInt("level.id") foreach { levelId =>
      builder.where("exists(from course.levels as cl where cl.level.id=:levelId)", levelId)
    }
    getBoolean("multiTerm") foreach { multiTerm =>
      if (multiTerm) builder.where("course.subCourse is not null")
      else builder.where("course.subCourse is null")
    }
    populateConditions(builder)
    builder.where(simpleEntityName + ".project = :project", getProject)
    QueryHelper.addActive(builder, getBoolean("active"))
    builder.orderBy(get(org.beangle.commons.collection.Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def persist(course: Course): JsonObject = {
    given project: Project = getProject
    if (entityDao.duplicate(classOf[Course], course.id, Map("project" -> project, "code" -> course.code))) {
      throw new IllegalArgumentException("另一个课程已经被使用了改课程代码，请更换一个")
    }
    if (formSubmitted("course.name") || formSubmitted("levelId") || formSubmitted("category.id")) {
      val teachingNatures = getCodes(classOf[TeachingNature])
      teachingNatures foreach { ht =>
        val creditHour = getInt("creditHour" + ht.id)
        course.hours find (h => h.nature == ht) match {
          case Some(hour) =>
            if (creditHour.isEmpty) course.hours -= hour
            else hour.creditHours = creditHour.getOrElse(0)
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
    }
    if (course.journals.isEmpty) {
      course.journals += new CourseJournal(course, course.beginOn)
    }
    val json = super.persist(course)
    if (course.journals.nonEmpty) databus.publish(DataEvent.update(course.journals))
    json
  }

  @response
  def activate(): JsonObject = {
    val courses = entityDao.find(classOf[Course], getLongIds("course"))
    val isActivate = getBoolean("isActivate", true)
    if (isActivate) courses.foreach { c => c.endOn = None; c.updatedAt = java.time.Instant.now }
    else {
      val yesterday = java.time.LocalDate.now.minusDays(1)
      courses.foreach { c => c.endOn = Some(yesterday); c.updatedAt = java.time.Instant.now }
    }
    saveMore(courses)
    val rs = new JsonObject()
    rs.add("updated", courses.size)
    rs
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    val project = getProject
    fl.addScope(classOf[org.openurp.base.model.Department], Map("school" -> project.school))
    setting.listeners = List(fl, new org.openurp.base.web.helper.CourseImportListener(entityDao, getProject))
  }

  /** 批量检查英文名。 */
  @response
  def checkEnName(): JsonObject = {
    val courses = entityDao.find(classOf[Course], getLongIds("course"))
    val messages = Collections.newBuffer[JsonObject]
    courses foreach { course =>
      course.enName match {
        case None =>
          val item = new JsonObject()
          item.add("id", course.id)
          item.add("code", course.code)
          item.add("message", "缺少英文名")
          messages.addOne(item)
        case Some(enName) =>
          val rs = Json.parse(HttpUtils.get(Ems.api + "/tools/lang/en/check.json?name=" +
            java.net.URLEncoder.encode(enName, Charsets.UTF_8)).getText).asInstanceOf[JsonObject]
          if (!rs.getBoolean("success")) {
            val item = new JsonObject()
            item.add("id", course.id)
            item.add("code", course.code)
            item.add("message", rs.getArray("data").map(_.toString).mkString(";"))
            messages.addOne(item)
          }
      }
    }
    val result = new JsonObject()
    result.add("messages", messages)
    result
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
    val sheet = schema.createSheet("数据模板")
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
    sheet.add("是否设置补考", "course.hasMakeup").bool()
    sheet.add("是否计算绩点", "course.calgp").bool()
    sheet.add("设立日期", "course.beginOn").date().required()
    sheet.add("有效期至", "course.endOn").date()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "课程模板.xlsx")
  }
}

/** /api/base/admin/edu/textbooks */
class TextbookWS extends ProjectAdminJsonWS[Textbook], ExportSupport[Textbook], JsonImportSupport[Textbook] {

  override protected def getQueryBuilder: OqlBuilder[Textbook] = {
    val builder = super.getQueryBuilder
    getBoolean("active") foreach {
      case true => builder.where("textbook.endOn is null or textbook.endOn >=:now", LocalDate.now)
      case false => builder.where("textbook.endOn is not null and textbook.endOn <:now", LocalDate.now)
    }
    builder
  }

  override protected def prepare(book: Textbook): Unit = {
    if (book.isbn.nonEmpty && Strings.isNotBlank(book.isbn.get)) {
      book.isbn = Some(formatISBN(book.isbn.get))
    }
    if (null == book.beginOn) book.beginOn = LocalDate.now
    if (book.isbn.nonEmpty && entityDao.duplicate(classOf[Textbook], book.id, Map("isbn" -> book.isbn))) {
      throw new IllegalArgumentException("ISBN 重复")
    }
    get("press.name") foreach { p =>
      if (Strings.isNotBlank(p)) {
        val pn = p.trim()
        val q = OqlBuilder.from(classOf[Press], "p").where("p.name=:name or p.name like :name2", pn, pn + "%")
        val presses = entityDao.search(q)
        if (presses.size == 1) {
          book.press = presses.headOption
        } else {
          val np = new Press
          np.name = pn
          np.code = pn
          np.beginOn = LocalDate.now
          np.grade = new PressGrade(PressGrade.Other)
          np.updatedAt = Instant.now
          entityDao.saveOrUpdate(np)
          book.press = Some(np)
        }
      }
    }
  }

  private def formatISBN(isbn: String): String = {
    if (isbn.length > 13) Strings.replace(isbn, "-", "") else isbn
  }

  @response
  @mapping("{id}")
  override def info(id: String): JsonObject = {
    val json = super.info(id)
    val textbook = entityDao.get(classOf[Textbook], id.toLong)
    val cq = OqlBuilder.from(classOf[CourseTextbook], "ct")
    cq.where("ct.textbook=:book", textbook)
    val courses = entityDao.search(cq).map(_.course).distinct.map { course =>
      val item = new JsonObject()
      item.add("id", course.id)
      item.add("code", course.code)
      item.add("name", course.name)
      item.add("defaultCredits", course.defaultCredits)
      item
    }
    val related = new JsonObject()
    related.add("courses", courses)
    json.add("related", related)
    json
  }

  /** 按 ISBN 预览外部书目，对应教材页的 batchAddForm。 */
  @response
  def batchAddForm(): JsonObject = {
    val rs = new JsonObject()
    get("isbn") foreach { line =>
      val isbns = Strings.split(line)
      val datas = isbns.map(isbn => org.openurp.base.web.helper.TextbookHelper.fetchByIsbn(isbn))
      rs.add("isbns", isbns)
      rs.add("books", datas)
    }
    rs
  }

  /** 按 ISBN 批量新增教材。 */
  @response
  def batchAdd(): JsonObject = {
    var newBookCount = 0
    val helper = org.openurp.base.web.helper.TextbookHelper
    given project: Project = getProject
    getAll("book.isbn", classOf[String]) foreach { i =>
      val isbn = formatISBN(i.trim())
      if (!entityDao.duplicate(classOf[Textbook], null, Map("isbn" -> isbn, "project" -> project))) {
        val data = helper.fetchByIsbn(isbn)
        if data.contains("name") then {
          val book = new Textbook
          book.isbn = Some(data.getString("isbn"))
          book.name = data.getString("name")
          book.author = data.getString("author")
          book.edition = data.getString("edition")
          book.publishedIn = YearMonth.parse(data.getString("publishedIn"))
          book.project = project
          book.beginOn = LocalDate.now
          book.creator = Some(getUser)
          val p = data.getString("press")
          if (Strings.isNotBlank(p)) {
            val pn = p.trim()
            val q = OqlBuilder.from(classOf[Press], "p").where("p.name=:name or p.name like :name2", pn, pn + "%")
            val presses = entityDao.search(q)
            if (presses.size == 1) book.press = presses.headOption
            else {
              val np = new Press
              np.name = pn
              np.code = pn
              np.beginOn = LocalDate.now
              np.grade = new PressGrade(PressGrade.Other)
              np.updatedAt = Instant.now
              entityDao.saveOrUpdate(np)
              book.press = Some(np)
            }
          }
          if (book.domestic) book.foreignBookType = None
          newBookCount += 1
          entityDao.saveOrUpdate(book)
        }
      }
    }
    val rs = new JsonObject()
    rs.add("created", newBookCount)
    rs
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new org.openurp.base.web.helper.TextbookImportListener(getProject, entityDao))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val presses = getCodes(classOf[Press]).sortBy(_.code).map(x => x.code + " " + x.name)
    val categories = getCodes(classOf[BookCategory]).sortBy(_.code).map(x => x.code + " " + x.name)
    val bookTypes = getCodes(classOf[BookType]).sortBy(_.code).map(x => x.code + " " + x.name)
    val awardTypes = getCodes(classOf[BookAwardType]).sortBy(_.code).map(x => x.code + " " + x.name)
    val foreignBookTypes = getCodes(classOf[BookAwardType]).sortBy(_.code).map(x => x.code + " " + x.name)
    val bookForms = getCodes(classOf[TextbookForm]).sortBy(_.code).map(x => x.code + " " + x.name)
    val disciplineCategories = getCodes(classOf[DisciplineCategory]).sortBy(_.code).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("教材信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("ISBN", "textbook.isbn").length(13).required().remark("≤13位")
    sheet.add("名称", "textbook.name").length(100).required()
    sheet.add("作者", "textbook.author").length(50).required()
    sheet.add("译者", "textbook.translator").length(50)
    sheet.add("出版社", "textbook.press.code").ref(presses).required()
    sheet.add("版次", "textbook.edition").length(20).required()
    sheet.add("出版年月", "textbook.publishedIn").date("YYYY-MM").required()
    sheet.add("是否自编", "textbook.madeInSchool").bool()
    sheet.add("教材类型", "textbook.bookType.code").ref(bookTypes)
    sheet.add("图书分类", "textbook.category.code").ref(categories)
    sheet.add("获奖级别", "textbook.awardType.code").ref(awardTypes)
    sheet.add("颁奖单位", "textbook.awardOrg").length(50)
    sheet.add("丛书", "textbook.series").length(100)
    sheet.add("价格", "textbook.price").decimal()
    sheet.add("是否境内教材", "textbook.domestic").bool()
    sheet.add("境外教材类型", "textbook.foreignBookType.code").ref(foreignBookTypes)
    sheet.add("教材形态", "textbook.bookForm.code").ref(bookForms)
    sheet.add("学科门类", "textbook.disciplineCategory.code").ref(disciplineCategories)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教材模板.xlsx")
  }
}

/** /api/base/admin/edu/teaching-offices */
class TeachingOfficeWS extends ProjectAdminJsonWS[TeachingOffice], ExportSupport[TeachingOffice], JsonImportSupport[TeachingOffice] {

  override protected def prepare(office: TeachingOffice): Unit = {
    if (!office.persisted && null == office.beginOn) office.beginOn = LocalDate.now
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fk = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fk.addForeigerKey("code")
    setting.listeners = List(fk, new org.openurp.base.web.helper.TeachingOfficeImportListener(getProject, entityDao))
  }

  @response
  def downloadTemplate(): Any = {
    val project = getProject
    val departs = project.departments.map(x => x.code + " " + x.name).toSeq.sorted
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("教研室信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("教研室代码", "teachingOffice.code").length(10).required().remark("≤10位")
    sheet.add("教研室名称", "teachingOffice.name").length(100).required()
    sheet.add("院系代码", "teachingOffice.department.code").ref(departs).required()
    sheet.add("负责人工号", "director.code")
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教研室模板.xlsx")
  }
}

/** /api/base/admin/edu/time-settings */
class TimeSettingWS extends ProjectAdminJsonWS[TimeSetting]
