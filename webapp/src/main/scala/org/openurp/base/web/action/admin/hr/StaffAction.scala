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
import org.beangle.web.action.annotation.{mapping, param, response}
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.execution.MappingHandler
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.hr.model.{Mentor, Staff, StaffTitle, Teacher}
import org.openurp.base.model.*
import org.openurp.base.service.Features.Hr
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{StaffImportListener, UrpUserHelper}
import org.openurp.code.edu.model.{Degree, DegreeLevel, EducationDegree}
import org.openurp.code.hr.model.{StaffType, WorkStatus}
import org.openurp.code.job.model.{ProfessionalTitle, TutorType}
import org.openurp.code.person.model.{Gender, IdType, Nation, PoliticalStatus}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDate}

class StaffAction extends ProjectRestfulAction[Staff], ExportSupport[Staff], ImportSupport[Staff] {

  var urpUserHelper: UrpUserHelper = _

  override def getQueryBuilder: OqlBuilder[Staff] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
    put("staffTypes", entityDao.getAll(classOf[StaffType]))
    put("statuses", codeService.get(classOf[WorkStatus]))
    put("titles", codeService.get(classOf[ProfessionalTitle]))
  }

  override def editSetting(staff: Staff) = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
    put("genders", codeService.get(classOf[Gender]))
    put("idTypes", codeService.get(classOf[IdType]))
    put("staffTypes", codeService.get(classOf[StaffType]))
    put("statuses", codeService.get(classOf[WorkStatus]))
    put("nations", codeService.get(classOf[Nation]))
    put("politicalStatuses", codeService.get(classOf[PoliticalStatus]))
    put("titles", codeService.get(classOf[ProfessionalTitle]))

    put("degrees", codeService.get(classOf[Degree]))
    put("educationDegrees", codeService.get(classOf[EducationDegree]))
    put("degreeLevels", codeService.get(classOf[DegreeLevel]))
    put("tutorTypes", codeService.get(classOf[TutorType]))

    put("extraRequired", Strings.split(getConfig(Hr.StaffExtraRequiredProperties).asInstanceOf[String]).toSet)
    if !staff.persisted then staff.beginOn = LocalDate.now
    super.editSetting(staff)
  }

  override protected def saveAndRedirect(staff: Staff): View = {
    given p: Project = getProject

    staff.school = p.school
    staff.updatedAt = Instant.now
    try {
      var oldCode: Option[String] = None
      if (staff.persisted) {
        val existQuery = OqlBuilder.from[String](classOf[Staff].getName, "t").select("t.code")
        existQuery.where("t.id = :staffId", staff.id)
        entityDao.search(existQuery).headOption foreach { code => oldCode = Some(code) }
      }
      entityDao.saveOrUpdate(staff)
      urpUserHelper.createUser(staff, oldCode)
      //synchronize name to teacher/mentor/tutor
      val teachers = entityDao.findBy(classOf[Teacher], "staff", staff)
      if (getConfig(Hr.TeacherSameDepartWithStaff).asInstanceOf[Boolean]) {
        teachers foreach (t => t.department = staff.department)
      }
      teachers foreach (t => t.name = staff.name)
      entityDao.saveOrUpdate(teachers)

      val mentors = entityDao.findBy(classOf[Mentor], "staff", staff)
      mentors foreach (t => t.name = staff.name)
      entityDao.saveOrUpdate(mentors)

      redirect("search", "info.save.success")
    } catch {
      case e: Exception =>
        val mapping = ActionContext.current.handler.asInstanceOf[MappingHandler].mapping
        val redirectTo = mapping.method.getName match {
          case "save" => "editNew"
          case "update" => "edit"
        }
        logger.info("save forward failure", e)
        redirect(redirectTo, "info.save.failure")
    }
  }

  override protected def removeAndRedirect(entities: Seq[Staff]): View = {
    val project = getProject
    val query = OqlBuilder.from(classOf[User], "u")
    query.where("u.code in(:codes) and u.school = :school", entities.map(_.code), project.school)
    val users = entityDao.search(query)
    entityDao.execute(Operation.remove(users).remove(entities))
    redirect("search", "info.remove.success")
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
    val sheet = schema.createScheet("数据模板")
    sheet.title("教职工信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("姓名", "staff.name").length(100).required()
    sheet.add("性别", "staff.gender.code").ref(genders).required()
    sheet.add("证件类型", "staff.idType.code").ref(idTypes)
    sheet.add("证件号码", "staff.idNumber").length(18)
    sheet.add("出生日期", "staff.birthday").date()

    sheet.add("民族", "staff.nation.code").ref(nations)
    sheet.add("政治面貌", "staff.politicalStatus.code").ref(politicalStatuses)
    sheet.add("教职工类别", "staff.staffType.code").ref(staffTypes).required()
    if (tutorTypes.size > 0) {
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
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "教职工信息.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new StaffImportListener(entityDao, getProject, urpUserHelper))
  }

  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val staff = entityDao.get(classOf[Staff], id.toLong)
    val titles = entityDao.findBy(classOf[StaffTitle], "staff", staff)
    put("staff", staff)
    put("staffTitles", titles)
    forward()
  }
}
