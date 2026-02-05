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
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.transfer.importer.ImportSetting
import org.beangle.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.support.helper.QueryHelper
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.base.edu.model.Major
import org.openurp.base.hr.model.{Staff, TutorJournal, TutorMajor}
import org.openurp.base.model.*
import org.openurp.base.std.model.Student
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{TutorImportListener, UrpUserHelper}
import org.openurp.code.edu.model.DegreeLevel
import org.openurp.code.hr.model.WorkStatus
import org.openurp.code.job.model.{ProfessionalGrade, ProfessionalTitle, TutorType}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

/** 导师管理
 */
class TutorAction extends ProjectRestfulAction[Staff], ExportSupport[Staff], ImportSupport[Staff] {
  var urpUserHelper: UrpUserHelper = _

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("tutorTypes", codeService.get(classOf[TutorType]))
    put("departments", findInSchool(classOf[Department]))
    put("statuses", codeService.get(classOf[WorkStatus]))
    put("titles", codeService.get(classOf[ProfessionalTitle]))
    put("professionalGrades", getCodes(classOf[ProfessionalGrade]))
    put("majors", findInProject(classOf[Major]))
    put("degreeLevels", getCodes(classOf[DegreeLevel]))
  }

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
          if (ages(0) == 0) query.where(s"staff.birthday is null")
          else query.where(s"age_year(staff.birthday) = :age", ages(0))
        } else if (ages.length == 2) {
          query.where(s"age_year(staff.birthday) between :ageBegin and :ageEnd", ages(0), ages(1))
        }
      }
    }
    getInt("majorEduLevelId") foreach { majorEduLevelId =>
      query.where(s"exists(from ${classOf[TutorMajor].getName} tm where tm.staff=staff and tm.level.id=:majorEduLevelId)", majorEduLevelId)
    }
    getBoolean("degreeAwardOutside") foreach { outside =>
      if (outside) {
        query.where(s"staff.degreeAwardBy != :school", getProject.school.name)
      } else {
        query.where(s"staff.degreeAwardBy is null or staff.degreeAwardBy == :school", getProject.school.name)
      }
    }
    query
  }

  override def editSetting(tutor: Staff):Unit = {
    given project: Project = getProject

    put("tutorTypes", codeService.get(classOf[TutorType]))
    val appointDates = entityDao.findBy(classOf[TutorJournal], "staff", tutor).map(x => (x.tutorType, x.beginOn)).toMap
    put("appointDates", appointDates)
    if (!tutor.persisted) {
      val query = OqlBuilder.from(classOf[Staff], "s")
      query.where("s.tutorType is null")
      query.where("s.school = :school", project.school)
      query.orderBy("s.code")
      put("staffs", entityDao.search(query))
      tutor.beginOn = LocalDate.now
    }
    super.editSetting(tutor)
  }

  override protected def saveAndRedirect(staff: Staff): View = {
    val project = getProject
    entityDao.saveOrUpdate(staff)
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
              val n = new TutorJournal(staff, tt, appointOn)
              entityDao.saveOrUpdate(n)
          }
      }
    }
    redirect("search", "info.save.success")
  }

  /** 消除导师属性
   *
   * @param entities
   * @return
   */
  override protected def removeAndRedirect(entities: Seq[Staff]): View = {
    entities foreach { s => s.tutorType = None }
    entityDao.saveOrUpdate(entities)
    redirect("search", "info.remvoe.success")
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val tutorTypes = codeService.get(classOf[TutorType]).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("导师信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    if tutorTypes.nonEmpty then sheet.add("导师类别", "staff.tutorType.code").ref(tutorTypes)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "导师信息.xlsx")
  }

  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val staff = entityDao.get(classOf[Staff], id.toLong)
    val majors = entityDao.findBy(classOf[TutorMajor], "staff", staff)
    val appointOn = entityDao.findBy(classOf[TutorJournal], "staff", staff).map(x => (x.tutorType, x.beginOn)).toMap
    put("staff", staff)
    put("majors", majors)
    put("appointOn", appointOn)
    forward()
  }

  /** Simple list staffs
   *
   * @return
   */
  def list(): View = {
    val query = getQueryBuilder
    query.limit(null)
    put("staffs", entityDao.search(query))
    forward("list-simple")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TutorImportListener(entityDao, getProject))
  }
}
