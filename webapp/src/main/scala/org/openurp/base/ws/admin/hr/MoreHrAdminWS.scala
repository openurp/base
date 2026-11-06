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
import org.beangle.commons.collection.Order
import org.beangle.commons.json.JsonObject
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.security.Securities
import org.beangle.she.webmvc.{ExportSupport, PopulateHelper, QueryHelper}
import org.beangle.webmvc.annotation.{action, response}
import org.beangle.webmvc.context.Params
import org.beangle.webmvc.view.Stream
import org.openurp.base.edu.model.{Major, MajorDirection, MajorDirector}
import org.openurp.base.hr.model.*
import org.openurp.base.model.{Department, Project}
import org.openurp.base.std.model.Grade
import org.openurp.base.web.helper.UrpUserHelper
import org.openurp.base.ws.admin.{AdminCodeWS, AdminJsonWS, JsonImportSupport, ProjectAdminJsonWS}
import org.openurp.code.edu.model.{EducationLevel, EducationType}
import org.openurp.code.hr.model.CadrePostRank
import org.openurp.starter.web.support.ProjectSupport

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}
import scala.compiletime.uninitialized

@action("code/{category}")
class HrCodeWS extends AdminCodeWS {
  override protected def codePackages: Set[String] =
    Set("org.openurp.code.hr.model", "org.openurp.code.job.model")
}

/** /api/base/admin/hr/secretaries */
class SecretaryWS extends ProjectAdminJsonWS[Secretary], JsonImportSupport[Secretary] {
  var urpUserHelper: UrpUserHelper = uninitialized

  override def getQueryBuilder: OqlBuilder[Secretary] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(secretary: Secretary): Unit = {
    val project = getProject
    if (null != secretary.staff) {
      val staff = entityDao.get(classOf[Staff], secretary.staff.id)
      secretary.staff = staff
      secretary.projects += project
      if (!secretary.persisted && null == secretary.beginOn) secretary.beginOn = LocalDate.now
    }
  }

  override protected def persist(secretary: Secretary): JsonObject = {
    val json = super.persist(secretary)
    if (null != secretary.staff) urpUserHelper.createUser(secretary.staff, None)
    json
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    setting.listeners = List(new org.openurp.base.web.helper.SecretaryImportListener(entityDao, getProject, urpUserHelper))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("辅导员信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("办公电话", "secretary.officePhone").length(10).remark("≤13位")
    sheet.add("办公室地址", "secretary.officeAddr").length(10)
    sheet.add("办公邮箱", "secretary.officeEmail").length(10)
    sheet.add("起始日期", "secretary.beginOn").date().remark("默认从今天开始")
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "教学秘书信息.xlsx")
  }
}

/** /api/base/admin/hr/cadre-assignments */
class CadreAssignmentWS extends ProjectAdminJsonWS[CadreAssignment], JsonImportSupport[CadreAssignment] {

  override protected def simpleEntityName: String = "assignment"

  override def getQueryBuilder: OqlBuilder[CadreAssignment] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(assignment: CadreAssignment): Unit = {
    if (assignment.department == null && null != assignment.staff) {
      assignment.department = entityDao.get(classOf[Staff], assignment.staff.id).department
    }
    if (!assignment.persisted && null == assignment.beginOn) assignment.beginOn = LocalDate.now
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    setting.listeners = List(fl, new org.openurp.base.web.helper.CadreAssignmentImportListener(entityDao, getProject.school))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.name")).map(x => x.code + " " + x.name)
    val ranks = codeService.get(classOf[CadrePostRank]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("领导干部信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("部门", "assignment.department.code").ref(departs).remark("默认为所在部门")
    sheet.add("职务级别", "assignment.rank.code").ref(ranks).required()
    sheet.add("行政职务", "assignment.post").length(300).required()
    sheet.add("是否兼任", "assignment.concurrent").bool()
    sheet.add("是否主职", "assignment.principal").bool()
    sheet.add("起始日期", "assignment.beginOn").date()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "领导干部信息任职.xlsx")
  }
}

/** /api/base/admin/hr/presidents */
class PresidentWS extends ProjectAdminJsonWS[President] {

  override def getQueryBuilder: OqlBuilder[President] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(president: President): Unit = {
    if (president.school == null) president.school = getProject.school
    if (!president.persisted && null == president.beginOn) president.beginOn = LocalDate.now
  }
}

/** /api/base/admin/hr/major-directors */
class MajorDirectorWS extends AdminJsonWS[MajorDirector], ProjectSupport {

  override protected def simpleEntityName: String = "director"

  override protected def getQueryBuilder: OqlBuilder[MajorDirector] = {
    val query = super.getQueryBuilder
    queryByDepart(query, "director.director.department")
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def prepare(director: MajorDirector): Unit = {
    if (null == director.beginOn) director.beginOn = LocalDate.now.minusDays(1)
  }
}

/** /api/base/admin/hr/tutor-majors */
class TutorMajorWS extends AdminJsonWS[TutorMajor], ProjectSupport, ExportSupport[TutorMajor], JsonImportSupport[TutorMajor] {

  override protected def getQueryBuilder: OqlBuilder[TutorMajor] = {
    val query = super.getQueryBuilder
    val directionName = get("direction.name", "")
    if (Strings.isNotBlank(directionName)) {
      query.where("exists(from tutorMajor.directions d where d.name like :directionName)", "%" + directionName + "%")
    }
    query
  }

  override protected def prepare(tm: TutorMajor): Unit = {
    if (formSubmitted("tutorMajor.grade.id") || formSubmitted("direction.id")) {
      val directionIds = getAll("direction.id", classOf[Long])
      val newDirections = entityDao.find(classOf[MajorDirection], directionIds)
      tm.directions.clear()
      tm.directions.addAll(newDirections)
    }
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val project = getProject
    val fl = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    fl.addScope(classOf[org.openurp.base.std.model.Grade], Map("project" -> project))
    setting.listeners = List(fl, new org.openurp.base.web.helper.TutorMajorImportListener(entityDao, project))
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject
    val levels = codeService.get(classOf[EducationLevel]).map(x => x.code + " " + x.name)
    val eduTypes = codeService.get(classOf[EducationType]).map(x => x.code + " " + x.name)
    val majors = findInProject(classOf[Major]).map(x => x.code + " " + x.name)
    val grades = findInProject(classOf[Grade]).map(x => x.code)
    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("导师研究领域信息模板")
    sheet.add("年级", "tutorMajor.grade.code").ref(grades).required()
    sheet.add("导师工号", "tutorMajor.staff.code").length(20).required().remark("≤20位")
    sheet.add("培养层次", "tutorMajor.level.code").ref(levels).required()
    sheet.add("培养类型", "tutorMajor.eduType.code").ref(eduTypes).required()
    sheet.add("专业", "tutorMajor.major.code").ref(majors).required()
    sheet.add("研究方向", "directionNames").required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "导师研究领域.xlsx")
  }
}

/** /api/base/admin/hr/tutor-journals */
class TutorJournalWS extends AdminJsonWS[TutorJournal]

/** /api/base/admin/hr/profiles */
class ProfileWS extends AdminJsonWS[StaffProfile], ProjectSupport {
  override def simpleEntityName: String = "profile"

  /** 当前学校中还没有档案的教职工。 */
  @response
  def missings(): JsonObject = {
    given project: Project = getProject
    val query = OqlBuilder.from(classOf[Staff], "staff")
    query.where("staff.school=:school", project.school)
    query.where(s"not exists(from ${classOf[StaffProfile].getName} p where p.staff=staff)")
    populateConditions(query)
    val sort = Params.get(Order.OrderStr) match {
      case orderBy@Some(_) => orderBy
      case None => Params.get("sort")
    }
    sort foreach { orderClause =>
      if orderClause.startsWith("staff") then query.orderBy(orderClause)
    }
    query.limit(getPageLimit)
    val staffs = entityDao.search(query)
    val items = staffs.map { staff =>
      val item = new JsonObject()
      item.add("id", staff.id)
      item.add("code", staff.code)
      item.add("name", staff.name)
      item
    }
    val rs = new JsonObject()
    rs.add("staffs", items)
    rs
  }
}

/** 当前登录人的教职工档案。/api/base/admin/hr/my-profile.jsonapi */
@action("my-profile")
class MyProfileWS extends AdminJsonWS[StaffProfile], ProjectSupport {

  override def simpleEntityName: String = "profile"

  override def getQueryBuilder: OqlBuilder[StaffProfile] = {
    val query = super.getQueryBuilder
    getStaff foreach { staff =>
      query.where("profile.staff=:staff", staff)
    }
    query
  }

  override protected def prepare(profile: StaffProfile): Unit = {
    getStaff foreach { staff =>
      profile.staff = staff
      PopulateHelper.populate(entityDao, profile.staff, Params.sub("staff"))
    }
    profile.updatedAt = Instant.now
  }

  @response
  def mine(): Option[StaffProfile] = {
    getStaff.flatMap { staff =>
      val builder = OqlBuilder.from(classOf[StaffProfile], "tb")
      builder.where("tb.staff=:staff", staff)
      entityDao.search(builder).headOption
    }
  }

  private def getStaff: Option[Staff] = {
    val query = OqlBuilder.from(classOf[Staff], "t")
    query.where("t.code=:code", Securities.user)
    entityDao.search(query).headOption
  }
}
