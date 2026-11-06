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

package org.openurp.base.ws.admin.hr

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.lang.{Charsets, Strings}
import org.beangle.commons.net.http.HttpUtils
import org.beangle.data.dao.{Operation, OqlBuilder}
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.ems.app.Ems
import org.beangle.she.webmvc.{ExportSupport, QueryHelper}
import org.beangle.webmvc.annotation.{mapping, response}
import org.beangle.webmvc.view.Stream
import org.openurp.base.edu.model.TeachingOffice
import org.openurp.base.hr.model.{Mentor, Staff, StaffTitle, Teacher, TutorJournal, TutorMajor}
import org.openurp.base.model.{Campus, Department, Project, User}
import org.openurp.base.service.Features.Hr
import org.openurp.base.std.model.{Squad, Student}
import org.openurp.base.web.helper.UrpUserHelper
import org.openurp.base.ws.admin.{JsonImportSupport, ProjectAdminJsonWS}
import org.openurp.code.edu.model.{Degree, DegreeLevel, EducationDegree}
import org.openurp.code.hr.model.{StaffType, WorkStatus}
import org.openurp.code.job.model.{ProfessionalTitle, TutorType}
import org.openurp.code.person.model.{Gender, IdType, Nation, PoliticalStatus}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate
import scala.compiletime.uninitialized

/** /api/base/admin/hr/staffs */
class StaffWS extends ProjectAdminJsonWS[Staff], ExportSupport[Staff], JsonImportSupport[Staff] {
  var urpUserHelper: UrpUserHelper = uninitialized

  override def getQueryBuilder: OqlBuilder[Staff] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(staff: Staff): Unit = {
    given p: Project = getProject
    staff.school = p.school
    if (entityDao.duplicate(classOf[Staff], staff.id, Map("school" -> p.school, "code" -> staff.code))) {
      throw new IllegalArgumentException("账户已经被使用了，请更换一个工号")
    }
    if (!staff.persisted && null == staff.beginOn) staff.beginOn = LocalDate.now
  }

  override protected def persist(staff: Staff): JsonObject = {
    val newStaff = !staff.persisted
    var oldCode: Option[String] = None
    if (staff.persisted) {
      val existQuery = OqlBuilder.from[String](classOf[Staff].getName, "t").select("t.code")
      existQuery.where("t.id = :staffId", staff.id)
      entityDao.search(existQuery).headOption foreach { code => oldCode = Some(code) }
    }
    val json = super.persist(staff)
    given Project = getProject
    urpUserHelper.createDepart(entityDao.get(classOf[Department], staff.department.id))
    urpUserHelper.createUser(staff, oldCode)
    if (newStaff && getBoolean("isTeacher", false)) {
      val teacher = new Teacher
      teacher.id = staff.id
      teacher.name = staff.name
      teacher.staff = staff
      teacher.department = staff.department
      teacher.projects.addAll(entityDao.findBy(classOf[Project], "school", staff.school))
      teacher.beginOn = staff.beginOn
      entityDao.saveOrUpdate(teacher)
      urpUserHelper.createUser(staff, None)
    }
    val teachers = entityDao.findBy(classOf[Teacher], "staff", staff)
    if (getConfig(Hr.TeacherSameDepartWithStaff).asInstanceOf[Boolean]) {
      teachers foreach (t => t.department = staff.department)
    }
    teachers foreach (t => t.name = staff.name)
    entityDao.saveOrUpdate(teachers)
    val mentors = entityDao.findBy(classOf[Mentor], "staff", staff)
    mentors foreach (t => t.name = staff.name)
    entityDao.saveOrUpdate(mentors)
    json
  }

  @response
  @mapping("{id}")
  override def info(id: String): JsonObject = {
    val json = super.info(id)
    val staff = entityDao.get(classOf[Staff], id.toLong)
    val titles = entityDao.findBy(classOf[StaffTitle], "staff", staff).map { title =>
      val item = new JsonObject()
      item.add("id", title.id)
      item.add("title", title.title.name)
      item.add("beginOn", title.beginOn)
      item.add("endOn", title.endOn)
      item
    }
    val related = new JsonObject()
    related.add("staffTitles", titles)
    json.add("related", related)
    json
  }

  override protected def removeEntities(entities: Seq[Staff]): Unit = {
    if (entities.isEmpty) return
    val project = getProject
    val query = OqlBuilder.from(classOf[User], "u")
    query.where("u.code in(:codes) and u.school = :school", entities.map(_.code), project.school)
    val users = entityDao.search(query)
    entityDao.execute(Operation.remove(users).remove(entities))
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    given Project = getProject
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    fl.addScope(classOf[Department], Map("school" -> getProject.school))
    setting.listeners = List(fl, new org.openurp.base.web.helper.StaffImportListener(entityDao, getProject, urpUserHelper))
  }

  /** 批量更新姓名拼音。 */
  @response
  def batchUpdateEnName(): JsonObject = {
    val staffs = entityDao.find(classOf[Staff], getLongIds("staff"))
    val forceUpdate = getBoolean("forceUpdate", false)
    var updated = 0
    staffs foreach { staff =>
      if (staff.enName.isEmpty || forceUpdate) {
        val response = HttpUtils.get(Ems.api + "/tools/sns/person/pinyin/" + java.net.URLEncoder.encode(staff.name, Charsets.UTF_8) + ".json")
        if (response.isOk) {
          staff.enName = Some(response.getText.trim)
          updated += 1
        }
      }
    }
    entityDao.saveOrUpdate(staffs)
    val rs = new JsonObject()
    rs.add("updated", updated)
    rs
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val genders = getCodes(classOf[Gender]).map(x => x.code + " " + x.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.code")).map(x => x.code + " " + x.name)
    val staffTypes = getCodes(classOf[StaffType]).map(x => x.code + " " + x.name)
    val idTypes = getCodes(classOf[IdType]).map(x => x.code + " " + x.name)
    val workStatuses = getCodes(classOf[WorkStatus]).map(x => x.code + " " + x.name)
    val titles = getCodes(classOf[ProfessionalTitle]).map(x => x.code + " " + x.name)
    val tutorTypes = getCodes(classOf[TutorType]).map(x => x.code + " " + x.name)
    val nations = getCodes(classOf[Nation]).map(x => x.code + " " + x.name)
    val politicalStatuses = getCodes(classOf[PoliticalStatus]).map(x => x.code + " " + x.name)
    val degrees = codeService.get(classOf[Degree]).map(x => x.code + " " + x.name)
    val educationDegrees = codeService.get(classOf[EducationDegree]).map(x => x.code + " " + x.name)
    val degreeLevels = codeService.get(classOf[DegreeLevel]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("教职工信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(20).required().remark("≤20位")
    sheet.add("姓名", "staff.name").length(100).required()
    sheet.add("性别", "staff.gender.code").ref(genders).required()
    sheet.add("证件类型", "staff.idType.code").ref(idTypes)
    sheet.add("证件号码", "staff.idNumber").length(18)
    sheet.add("出生日期", "staff.birthday").date()
    sheet.add("民族", "staff.nation.code").ref(nations)
    sheet.add("政治面貌", "staff.politicalStatus.code").ref(politicalStatuses)
    sheet.add("教职工类别", "staff.staffType.code").ref(staffTypes).required()
    if (tutorTypes.nonEmpty) {
      sheet.add("导师类别", "staff.tutorType.code").ref(tutorTypes)
    }
    sheet.add("所在部门", "staff.department.code").ref(departs).required()
    sheet.add("职称", "staff.title.code").ref(titles)
    sheet.add("学历", "staff.educationDegree.code").ref(educationDegrees)
    sheet.add("学位水平", "staff.degreeLevel.code").ref(degreeLevels)
    sheet.add("学位", "staff.degree.code").ref(degrees)
    sheet.add("学位授予单位", "staff.degreeAwardBy").length(100)
    sheet.add("是否在编", "staff.formalHr").bool().required()
    sheet.add("是否外聘", "staff.external").bool().required()
    sheet.add("是否兼职", "staff.parttime").bool().required()
    sheet.add("全职工作单位", "staff.organization").length(100)
    sheet.add("电子邮件", "staff.email").length(100)
    sheet.add("移动电话", "staff.mobile").length(18)
    sheet.add("进校日期", "staff.beginOn").date().required()
    sheet.add("在职状态", "staff.status.code").ref(workStatuses).required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教职工信息.xlsx")
  }
}

/** /api/base/admin/hr/teachers */
class TeacherWS extends ProjectAdminJsonWS[Teacher], ExportSupport[Teacher], JsonImportSupport[Teacher] {
  var urpUserHelper: UrpUserHelper = uninitialized

  override def getQueryBuilder: OqlBuilder[Teacher] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(teacher: Teacher): Unit = {
    val project = getProject
    if (formSubmitted("teacher.department.id") || formSubmitted("teacherProjectId") || formSubmitted("campus.id")) {
      val projectIds = getAll("teacherProjectId", classOf[Int])
      val newProjects = entityDao.find(classOf[Project], projectIds)
      teacher.projects.clear()
      teacher.projects.addAll(newProjects)
      val campusIds = getIntIds("campus")
      val campuses = if campusIds.isEmpty then List.empty[Campus] else entityDao.find(classOf[Campus], campusIds)
      teacher.campuses.filter(campuses.contains)
      teacher.campuses.addAll(campuses)
    }
    if (!teacher.projects.contains(project)) teacher.projects.addOne(project)
    if (!teacher.persisted && null != teacher.staff) teacher.id = teacher.staff.id
    if (null != teacher.staff) {
      val staff = entityDao.get(classOf[Staff], teacher.staff.id)
      teacher.staff = staff
      teacher.name = staff.name
      if (!teacher.persisted && null == teacher.beginOn) teacher.beginOn = staff.beginOn
    }
  }

  override protected def persist(teacher: Teacher): JsonObject = {
    val json = super.persist(teacher)
    if (null != teacher.staff) urpUserHelper.createUser(teacher.staff, None)
    json
  }

  @response
  def activate(): JsonObject = {
    val teachers = entityDao.find(classOf[Teacher], getLongIds("teacher"))
    val isActivate = getBoolean("isActivate", true)
    if (isActivate) teachers.foreach { c => c.endOn = None }
    else {
      val yesterday = java.time.LocalDate.now.minusDays(1)
      teachers.foreach { c => c.endOn = Some(yesterday) }
    }
    entityDao.saveOrUpdate(teachers)
    val rs = new JsonObject()
    rs.add("updated", teachers.size)
    rs
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new org.openurp.base.web.helper.TeacherImportListener(entityDao, getProject, urpUserHelper))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.code")).map(x => x.code + " " + x.name)
    val teachingOffices = findInProject(classOf[TeachingOffice]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("教师信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("教师工号", "teacher.staff.code").length(10).required().remark("≤10位")
    sheet.add("教学所在部门", "teacher.department.code").ref(departs)
    if teachingOffices.nonEmpty then sheet.add("所在教研室", "teacher.office.code").ref(teachingOffices)
    sheet.add("任教起始日期", "teacher.beginOn").date()
    sheet.add("教师资格证号码", "teacher.tqcNumber").length(20)
    sheet.add("其他职业资格证书和等级说明", "teacher.oqc").length(100)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教师信息.xlsx")
  }
}

/** /api/base/admin/hr/tutors ，实体仍是教职工，只列出已任命导师。 */
class TutorWS extends ProjectAdminJsonWS[Staff], ExportSupport[Staff], JsonImportSupport[Staff] {

  override def getQueryBuilder: OqlBuilder[Staff] = {
    val query = super.getQueryBuilder
    query.where("staff.tutorType is not null")
    QueryHelper.addActive(query, getBoolean("active"))
    getInt("stdCount") foreach {
      case 0 => query.where(s"not exists(from ${classOf[Student].getName} s where s.tutor.staff = staff and s.endOn >= :endOn)", LocalDate.now)
      case stdCount@(1 | 2 | 3) => query.where(s"${stdCount} = (select count(*) from ${classOf[Student].getName} s where s.tutor.staff = staff and s.endOn >= :endOn)", LocalDate.now)
      case 4 => query.where(s"(select count(*) from ${classOf[Student].getName} s where s.tutor.staff = staff and s.endOn >= :endOn) >=4 ", LocalDate.now)
    }
    getLong("major.id") foreach { majorId =>
      getInt("staff.tutorType.id") match
        case None =>
          query.where(s"exists(from ${classOf[TutorMajor].getName} tm where tm.staff=staff and tm.major.id=:majorId)", majorId)
        case Some(tutorTypeId) =>
          val tutorType = entityDao.get(classOf[TutorType], tutorTypeId)
          val levelName = if tutorType.name.contains("博士") then "博士" else "硕士"
          query.where(s"exists(from ${classOf[TutorMajor].getName} tm where tm.staff=staff and tm.level.name like :levelName and tm.major.id=:majorId)", levelName, majorId)
    }
    get("age") foreach { age =>
      if (Strings.isNotBlank(age)) {
        val ages = Strings.split(age, "-").map(_.trim().toInt)
        if (ages.length == 1) {
          if (ages(0) == 0) query.where("staff.birthday is null")
          else query.where("age_year(staff.birthday) = :age", ages(0))
        } else if (ages.length == 2) {
          query.where("age_year(staff.birthday) between :ageBegin and :ageEnd", ages(0), ages(1))
        }
      }
    }
    getInt("majorEduLevelId") foreach { majorEduLevelId =>
      query.where(s"exists(from ${classOf[TutorMajor].getName} tm where tm.staff=staff and tm.level.id=:majorEduLevelId)", majorEduLevelId)
    }
    getBoolean("degreeAwardOutside") foreach { outside =>
      if (outside) query.where("staff.degreeAwardBy != :school", getProject.school.name)
      else query.where("staff.degreeAwardBy is null or staff.degreeAwardBy == :school", getProject.school.name)
    }
    query
  }

  override protected def persist(staff: Staff): JsonObject = {
    val json = super.persist(staff)
    if (formSubmitted("staff.tutorType.id")) {
      given project: Project = getProject
      val tutorTypes = codeService.get(classOf[TutorType])
      val journals = entityDao.findBy(classOf[TutorJournal], "staff", staff)
      tutorTypes foreach { tt =>
        val date = getDate(s"tutorType${tt.id}.appointOn")
        date match {
          case None =>
            journals.find(_.tutorType == tt) foreach { j => entityDao.remove(j) }
          case Some(appointOn) =>
            journals.find(_.tutorType == tt) match {
              case Some(j) =>
                j.beginOn = appointOn
                entityDao.saveOrUpdate(j)
              case None =>
                entityDao.saveOrUpdate(new TutorJournal(staff, tt, appointOn))
            }
        }
      }
    }
    json
  }

  @response
  def list(): JsonObject = {
    val query = getQueryBuilder
    query.limit(null)
    val staffs = entityDao.search(query).map { staff =>
      val item = new JsonObject()
      item.add("id", staff.id)
      item.add("code", staff.code)
      item.add("name", staff.name)
      item
    }
    val rs = new JsonObject()
    rs.add("staffs", staffs)
    rs
  }

  @response
  @mapping("{id}")
  override def info(id: String): JsonObject = {
    val json = super.info(id)
    val staff = entityDao.get(classOf[Staff], id.toLong)
    val majors = entityDao.findBy(classOf[TutorMajor], "staff", staff).map { major =>
      val item = new JsonObject()
      item.add("id", major.id)
      item.add("major", major.major.name)
      item.add("level", major.level.name)
      item
    }
    val appointOn = entityDao.findBy(classOf[TutorJournal], "staff", staff).map { journal =>
      val item = new JsonObject()
      item.add("tutorTypeId", journal.tutorType.id)
      item.add("tutorType", journal.tutorType.name)
      item.add("beginOn", journal.beginOn)
      item
    }
    val related = new JsonObject()
    related.add("majors", majors)
    related.add("appointOn", appointOn)
    json.add("related", related)
    json
  }

  override protected def removeEntities(entities: Seq[Staff]): Unit = {
    entities foreach { staff => staff.tutorType = None }
    if (entities.nonEmpty) entityDao.saveOrUpdate(entities)
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new org.openurp.base.web.helper.TutorImportListener(entityDao, getProject))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val tutorTypes = codeService.get(classOf[TutorType]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("导师信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    if tutorTypes.nonEmpty then sheet.add("导师类别", "staff.tutorType.code").ref(tutorTypes)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "导师信息.xlsx")
  }
}

/** /api/base/admin/hr/mentors */
class MentorWS extends ProjectAdminJsonWS[Mentor], ExportSupport[Mentor], JsonImportSupport[Mentor] {
  var urpUserHelper: UrpUserHelper = uninitialized

  override def getQueryBuilder: OqlBuilder[Mentor] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(mentor: Mentor): Unit = {
    val staff = entityDao.get(classOf[Staff], mentor.staff.id)
    if (!mentor.persisted) mentor.id = mentor.staff.id
    mentor.projects += getProject
    mentor.name = staff.name
    mentor.staff = staff
    if (!mentor.persisted && null == mentor.beginOn) mentor.beginOn = LocalDate.now
  }

  override protected def persist(mentor: Mentor): JsonObject = {
    val json = super.persist(mentor)
    urpUserHelper.createUser(mentor.staff, None)
    json
  }

  @response
  @mapping("{id}")
  override def info(id: String): JsonObject = {
    val json = super.info(id)
    val mentor = entityDao.get(classOf[Mentor], id.toLong)
    val squads = entityDao.findBy(classOf[Squad], "mentor", mentor.staff).map { squad =>
      val item = new JsonObject()
      item.add("id", squad.id)
      item.add("code", squad.code)
      item.add("name", squad.name)
      item.add("grade", squad.grade.code)
      item.add("level", squad.level.name)
      item.add("department", squad.department.name)
      item.add("stdCount", squad.stdCount)
      item
    }
    val related = new JsonObject()
    related.add("squads", squads)
    json.add("related", related)
    json
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    setting.listeners = List(new org.openurp.base.web.helper.MentorImportListener(entityDao, getProject, urpUserHelper))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("辅导员信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("起始日期", "mentor.beginOn").date().required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "辅导员信息.xlsx")
  }
}
