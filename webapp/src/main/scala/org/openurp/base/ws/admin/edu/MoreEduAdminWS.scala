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
import org.beangle.commons.collection.Order
import org.beangle.commons.json.JsonObject
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.pojo.TemporalOn
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.she.webmvc.{ExportSupport, QueryHelper}
import org.beangle.webmvc.annotation.{action, mapping, response}
import org.beangle.webmvc.view.Stream
import org.openurp.base.edu.model.*
import org.openurp.base.model.Project
import org.openurp.base.ws.admin.{AdminCodeWS, AdminJsonWS, JsonImportSupport, ProjectAdminJsonWS}
import org.openurp.code.edu.model.{CourseTag, TeachingNature}
import org.openurp.starter.web.support.ProjectSupport

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate
import scala.compiletime.uninitialized

@action("code/{category}")
class EduCodeWS extends AdminCodeWS {
  override protected def codePackages: Set[String] =
    Set("org.openurp.code.edu.model", "org.openurp.code.sin.model", "org.openurp.code.trd.model")
}

/** /api/base/admin/edu/directions */
class DirectionWS extends ProjectAdminJsonWS[MajorDirection], JsonImportSupport[MajorDirection] {

  override protected def simpleEntityName: String = "direction"

  override def getQueryBuilder: OqlBuilder[MajorDirection] = {
    val query = super.getQueryBuilder
    getInt("department.id") foreach { d =>
      query.where("exists(from direction.journals as dj where dj.depart.id=:departId)", d)
    }
    getInt("level.id") foreach { d =>
      query.where("exists(from direction.journals as dj where dj.level.id=:levelId)", d)
    }
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(direction: MajorDirection): Unit = {
    if (!direction.persisted && null == direction.beginOn) direction.beginOn = LocalDate.now
    if (!direction.persisted && null != direction.major) {
      val major = entityDao.get(classOf[Major], direction.major.id)
      val departs = major.journals.map(j => j.depart).toSet
      val levels = major.journals.map(j => j.level).toSet
      if (departs.size == 1 && levels.size == 1 && direction.journals.isEmpty) {
        val dj = new MajorDirectionJournal
        dj.direction = direction
        dj.depart = departs.head
        dj.level = levels.head
        dj.beginOn = LocalDate.now
        direction.journals += dj
      }
    }
  }

  override protected def persist(direction: MajorDirection): JsonObject = {
    val json = super.persist(direction)
    entityDao.evict(classOf[MajorDirection])
    json
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new org.openurp.base.web.helper.DirectionImportListener(entityDao, getProject))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val majors = entityDao.findBy(classOf[Major], "project", project).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("专业方向模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("方向代码", "direction.code").length(10).required().remark("≤10位")
    sheet.add("方向名称", "direction.name").length(100).required()
    sheet.add("方向英文名称", "direction.enName").length(300)
    sheet.add("所属专业", "direction.major.code").ref(majors)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "专业方向模板.xlsx")
  }
}

/** /api/base/admin/edu/major-journals */
class MajorJournalWS extends AdminJsonWS[MajorJournal], ProjectSupport {
  var databus: DataEventBus = uninitialized

  override protected def persist(journal: MajorJournal): JsonObject = {
    val json = super.persist(journal)
    entityDao.refresh(journal)
    entityDao.refresh(journal.major)
    if (journal.major.journals.nonEmpty) {
      journal.major.beginOn = journal.major.journals.map(_.beginOn).min
    }
    entityDao.saveOrUpdate(journal.major)
    entityDao.evict(classOf[Major])
    databus.publish(DataEvent.update(journal))
    databus.publish(DataEvent.update(journal.major))
    json
  }

  override protected def removeEntities(entities: Seq[MajorJournal]): Unit = {
    super.removeEntities(entities)
    if (entities.nonEmpty) {
      entityDao.evict(classOf[Major])
      databus.publish(DataEvent.remove(entities))
    }
  }
}

/** /api/base/admin/edu/direction-journals */
class DirectionJournalWS extends AdminJsonWS[MajorDirectionJournal], ProjectSupport {
  var databus: DataEventBus = uninitialized

  override protected def simpleEntityName: String = "directionJournal"

  override protected def persist(journal: MajorDirectionJournal): JsonObject = {
    val json = super.persist(journal)
    entityDao.evict(classOf[MajorDirection])
    val direction = entityDao.get(classOf[MajorDirection], journal.direction.id)
    direction.beginOn = direction.journals.map(_.beginOn).min
    entityDao.saveOrUpdate(direction)
    databus.publish(DataEvent.update(journal))
    json
  }

  override protected def removeEntities(entities: Seq[MajorDirectionJournal]): Unit = {
    super.removeEntities(entities)
    if (entities.nonEmpty) {
      entityDao.evict(classOf[MajorDirection])
      databus.publish(DataEvent.remove(entities))
    }
  }
}

/** /api/base/admin/edu/major-disciplines */
class MajorDisciplineWS extends AdminJsonWS[MajorDiscipline] {
  var databus: DataEventBus = uninitialized

  override protected def prepare(discipline: MajorDiscipline): Unit = {
    if (!discipline.persisted && null == discipline.beginOn) discipline.beginOn = LocalDate.now
  }

  override protected def persist(discipline: MajorDiscipline): JsonObject = {
    val json = super.persist(discipline)
    entityDao.evict(classOf[Major])
    databus.publish(DataEvent.update(discipline))
    json
  }

  override protected def removeEntities(entities: Seq[MajorDiscipline]): Unit = {
    super.removeEntities(entities)
    if (entities.nonEmpty) {
      entityDao.evict(classOf[Major])
      databus.publish(DataEvent.remove(entities))
    }
  }
}

/** /api/base/admin/edu/course-journals */
class CourseJournalWS extends ProjectAdminJsonWS[CourseJournal] {
  override def simpleEntityName: String = "journal"

  override def getQueryBuilder: OqlBuilder[CourseJournal] = {
    given project: Project = getProject
    val query = super.getQueryBuilder
    queryByDepart(query, "journal.department")
    query
  }

  override protected def persist(journal: CourseJournal): JsonObject = {
    if (formSubmitted("journal.name")) {
      given project: Project = getProject
      val teachingNatures = getCodes(classOf[TeachingNature])
      teachingNatures foreach { ht =>
        val creditHour = getInt("creditHour" + ht.id)
        journal.hours find (h => h.nature == ht) match {
          case Some(hour) =>
            if (creditHour.isEmpty) journal.hours -= hour
            else hour.creditHours = creditHour.getOrElse(0)
          case None =>
            if (creditHour.nonEmpty) {
              journal.hours += new CourseJournalHour(journal, ht, creditHour.getOrElse(0))
            }
        }
      }
      val orphan = journal.hours.filter(x => !teachingNatures.contains(x.nature))
      journal.hours --= orphan
      journal.tags.clear()
      journal.tags.addAll(entityDao.find(classOf[CourseTag], getIntIds("tag")))
    }
    val json = super.persist(journal)
    if (formSubmitted("journal.name") && null != journal.course) {
      val journals = TemporalOn.calcEndOn(entityDao.findBy(classOf[CourseJournal], "course", journal.course))
      entityDao.saveOrUpdate(journals)
      if (journals.nonEmpty) {
        val last = journals.last
        val course = entityDao.get(classOf[Course], last.course.id)
        if (last.endOn.isEmpty) {
          if (last.enName.nonEmpty) course.enName = last.enName
          course.updateHours(last.hours.map(x => (x.nature, x.creditHours)).toMap)
          course.creditHours = last.creditHours
          course.weekHours = last.weekHours
          course.weeks = last.weeks
          course.name = last.name
          course.department = last.department
          course.examMode = last.examMode
        }
        course.beginOn = journals.map(_.beginOn).min
        if (journals.exists(_.endOn.isEmpty)) course.endOn = None
        else course.endOn = Some(journals.map(_.endOn.get).max)
        saveMore(course)
      }
    }
    json
  }
}

/** /api/base/admin/edu/course-textbooks */
class CourseTextbookWS extends ProjectAdminJsonWS[CourseTextbook], ExportSupport[CourseTextbook], JsonImportSupport[CourseTextbook] {

  override protected def getQueryBuilder: OqlBuilder[CourseTextbook] = {
    val builder = OqlBuilder.from(classOf[CourseTextbook], simpleEntityName)
    populateConditions(builder)
    builder.where("courseTextbook.course.project=:project", getProject)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    builder.tailOrder(simpleEntityName + ".id")
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    setting.listeners = List(fl, new org.openurp.base.web.helper.CourseTextbookImportListener(entityDao, getProject))
  }

  @response
  def downloadTemplate(): Any = {
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("课程教材信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("课程代码", "courseTextbook.course.code").length(10).required().remark("≤10位")
    sheet.add("教材ISBN", "courseTextbook.textbook.isbn").length(100).required()
    sheet.add("是否推荐教材", "courseTextbook.recommended").bool()
    sheet.add("是否必选教材", "courseTextbook.required").bool()
    sheet.add("设立日期", "courseTextbook.beginOn").date().required()
    sheet.add("有效期至", "courseTextbook.endOn").date()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "课程默认教材模板.xlsx")
  }
}

/** /api/base/admin/edu/course-units */
class CourseUnitWS extends ProjectAdminJsonWS[CourseUnit] {

  override protected def getQueryBuilder: OqlBuilder[CourseUnit] = {
    val builder = OqlBuilder.from(classOf[CourseUnit], "courseUnit")
    builder.where("courseUnit.setting.project=:project", getProject)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }
}

/** /api/base/admin/edu/school-lengths */
class SchoolLengthWS extends AdminJsonWS[SchoolLength], ProjectSupport {

  override protected def persist(entity: SchoolLength): JsonObject = {
    val json = super.persist(entity)
    entityDao.evict(classOf[Major])
    json
  }
}

/** /api/base/admin/edu/minor-majors */
class MinorMajorWS extends ProjectAdminJsonWS[MinorMajor] {

  override def simpleEntityName: String = "major"

  override def getQueryBuilder: OqlBuilder[MinorMajor] = {
    val query = super.getQueryBuilder
    query.where("major.project = :project", getProject)
    query
  }

  /** 把当前项目里的专业导入成辅修专业。 */
  @response
  def init(): JsonObject = {
    val project = getProject
    val existsMajors = entityDao.findBy(classOf[MinorMajor], "project", project).groupBy(_.name)
    val majors = entityDao.findBy(classOf[Major], "project", project)
    var created = 0
    majors foreach { major =>
      if (!existsMajors.contains(major.name) && major.within(LocalDate.now)) {
        val newMajor = new MinorMajor
        newMajor.code = major.code
        newMajor.name = major.name
        newMajor.project = project
        newMajor.beginOn = major.beginOn
        newMajor.major = Some(major)
        newMajor.department = major.departmentsNow.headOption
        newMajor.institution = project.school.institution
        newMajor.category = major.disciplines.map(_.category).head
        entityDao.saveOrUpdate(newMajor)
        created += 1
      }
    }
    val rs = new JsonObject()
    rs.add("created", created)
    rs
  }
}
