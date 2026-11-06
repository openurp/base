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

package org.openurp.base.ws.admin.std

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.json.JsonObject
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.she.webmvc.ExportSupport
import org.beangle.webmvc.annotation.{mapping, response}
import org.beangle.webmvc.view.Stream
import org.openurp.base.edu.model.{Major, MajorDirection}
import org.openurp.base.std.model.{Grade, GraduateSeason, Squad, Student}
import org.openurp.base.std.service.SquadService
import org.openurp.base.ws.admin.{JsonImportSupport, ProjectAdminJsonWS}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate
import scala.compiletime.uninitialized

/** /api/base/admin/std/squads */
class SquadWS extends ProjectAdminJsonWS[Squad], ExportSupport[Squad], JsonImportSupport[Squad] {

  override def getQueryBuilder: OqlBuilder[Squad] = {
    val query = super.getQueryBuilder
    getBoolean("active") foreach { active =>
      val activeCon = s":now between ${query.alias}.beginOn and ${query.alias}.endOn"
      if (active) query.where(activeCon, LocalDate.now())
      else query.where(s"not(${activeCon})", LocalDate.now())
    }
    get("staff.name") foreach { name =>
      if (Strings.isNotEmpty(name)) {
        val nameParam = "%" + name + "%"
        query.where("exists(from squad.mentor m where m.name like :name)" +
          "or exists(from squad.master m where m.name like :name)", nameParam)
      }
    }
    query
  }

  var squadService: SquadService = uninitialized

  /** 自动指定学生 */
  @response
  def autoAssign(): JsonObject = {
    val squads = entityDao.find(classOf[Squad], getLongIds("squad"))
    squads foreach { squad =>
      val query = OqlBuilder.from(classOf[Student], "std")
      query.where("std.state.squad is null")
      query.where("std.level=:level", squad.level)
      query.where("std.state.grade=:grade", squad.grade)
      query.where("std.state.department=:department", squad.department)
      squad.stdType foreach { stdType =>
        query.where("std.stdType=:stdType", stdType)
      }
      squad.major foreach { major =>
        query.where("std.state.major=:major", major)
      }
      squad.direction foreach { direction =>
        query.where("std.state.direction=:direction", direction)
      }
      val stds = entityDao.search(query)
      stds foreach { std =>
        std.state.get.squad = Some(squad)
      }
      squad.stdCount = stds.size
      entityDao.saveOrUpdate(stds)
      entityDao.saveOrUpdate(squad)
    }
    val rs = new JsonObject()
    rs.add("updated", squads.size)
    rs
  }

  @response
  def removeStudent(): JsonObject = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    val std = entityDao.get(classOf[Student], getLongId("student"))
    if (std.state.get.squad.contains(squad)) {
      std.state.get.squad = None
      squad.stdCount -= 1
    }
    entityDao.saveOrUpdate(squad, std)
    val rs = new JsonObject()
    rs.add("stdCount", squad.stdCount)
    rs
  }

  @response
  def addStudent(): JsonObject = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    val codes = Strings.split(get("codes", ""))
    val stds = entityDao.findBy(classOf[Student], "project" -> squad.project, "code" -> codes)
    stds foreach { std =>
      if (std.state.get.squad.isEmpty) {
        std.state.get.squad = Some(squad)
        squad.stdCount += 1
      }
    }
    entityDao.saveOrUpdate(squad, stds)
    val rs = new JsonObject()
    rs.add("stdCount", squad.stdCount)
    rs
  }

  /** 统计班级人数 */
  @response
  def statStdCount(): JsonObject = {
    val squads = entityDao.find(classOf[Squad], getLongIds("squad"))
    val updated = squadService.statStdCount(squads)
    val rs = new JsonObject()
    rs.add("updated", updated)
    rs
  }

  /** 复制班级的表单预填，不落库。 */
  @response
  def copy(): JsonObject = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    val item = new JsonObject()
    item.add("projectId", squad.project.id)
    item.add("code", squad.code + "(copy)")
    item.add("name", squad.name + "(复制)")
    item.add("enName", squad.enName)
    item.add("shortName", squad.shortName)
    item.add("gradeId", squad.grade.id)
    item.add("levelId", squad.level.id)
    item.add("eduTypeId", squad.eduType.id)
    item.add("stdTypeId", squad.stdType.map(_.id))
    item.add("departmentId", squad.department.id)
    item.add("majorId", squad.major.map(_.id))
    item.add("directionId", squad.direction.map(_.id))
    item.add("beginOn", squad.beginOn)
    item.add("endOn", squad.endOn)
    item.add("campusId", squad.campus.map(_.id))
    item.add("planCount", squad.planCount)
    item.add("stdCount", squad.stdCount)
    item.add("mentorId", squad.mentor.map(_.id))
    item.add("masterId", squad.master.map(_.id))
    item
  }

  @response
  @mapping("{id}")
  override def info(id: String): JsonObject = {
    val json = super.info(id)
    val squad = entityDao.get(classOf[Squad], id.toLong)
    val students = squadService.getStudents(squad).map { student =>
      val item = new JsonObject()
      item.add("id", student.id)
      item.add("code", student.code)
      item.add("name", student.name)
      item.add("gender", student.gender.name)
      item
    }
    val related = new JsonObject()
    related.add("students", students)
    json.add("related", related)
    json
  }

  override protected def configImport(setting: org.beangle.transfer.importer.ImportSetting): Unit = {
    val fk = new org.beangle.transfer.importer.listener.ForeignerListener(entityDao)
    fk.addForeigerKey("code")
    setting.listeners = List(fk, new org.openurp.base.web.helper.SquadImportListener(getProject, entityDao))
  }

  @response
  def downloadTemplate(): Any = {
    val project = getProject
    val departs = project.departments.map(x => x.code + " " + x.name).toSeq.sorted
    val grades = entityDao.search(OqlBuilder.from(classOf[Grade], "m").where("m.project=:project", project).orderBy("m.code")).map(x => x.name)
    val majors = entityDao.search(OqlBuilder.from(classOf[Major], "m").where("m.project=:project", project).orderBy("m.code")).map(x => x.code + " " + x.name)
    val directions = entityDao.search(OqlBuilder.from(classOf[MajorDirection], "d").where("d.project=:project", project).orderBy("d.code")).map(x => x.code + " " + x.name)
    val campuses = project.campuses.toSeq.map(x => x.code + " " + x.name).toSeq.sorted
    val levels = project.levels.map(x => x.code + " " + x.name).toSeq.sorted
    val stdTypes = project.stdTypes.map(x => x.code + " " + x.name).toSeq.sorted
    val endTypes = project.eduTypes.map(x => x.code + " " + x.name).toSeq.sorted

    val schema = new ExcelSchema()
    val sheet = schema.createSheet("数据模板")
    sheet.title("班级信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("班级代码", "squad.code").length(15).required().remark("≤15位")
    sheet.add("班级名称", "squad.name").length(100).required()
    sheet.add("班级英文名称", "squad.enName").length(300)
    sheet.add("培养层次代码", "squad.level.code").ref(levels).required()
    sheet.add("培养类型代码", "squad.eduType.code").ref(endTypes).required()
    sheet.add("学生类别代码", "squad.stdType.code").ref(stdTypes).required()
    sheet.add("年级", "grade.name").ref(grades).required()
    sheet.add("院系代码", "squad.department.code").ref(departs).required()
    sheet.add("专业代码", "squad.major.code").ref(majors)
    sheet.add("专业方向代码", "squad.direction.code").ref(directions)
    sheet.add("计划人数", "squad.planCount").required().decimal()
    sheet.add("辅导员工号", "squad.mentor.code")
    sheet.add("班主任工号", "squad.master.code")
    sheet.add("校区代码", "squad.campus.code").ref(campuses)
    sheet.add("生效日期", "squad.beginOn").date().required()
    sheet.add("失效日期", "squad.endOn").date().required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "班级模板.xlsx")
  }
}

/** /api/base/admin/std/grades */
class GradeWS extends ProjectAdminJsonWS[Grade] {

  override def getQueryBuilder: OqlBuilder[Grade] = {
    val query = super.getQueryBuilder
    query.where("grade.project=:project", getProject)
    query
  }

  override protected def prepare(grade: Grade): Unit = {
    if (!grade.persisted) {
      val code = getProject.id.toString + Strings.replace(grade.code, "-", "")
      grade.id = code.toLong
    }
  }
}

/** /api/base/admin/std/graduate-seasons */
class GraduateSeasonWS extends ProjectAdminJsonWS[GraduateSeason] {

  override def getQueryBuilder: OqlBuilder[GraduateSeason] = {
    val query = super.getQueryBuilder
    import query.given
    query.where(_.project.equal(getProject))
    query
  }

  override protected def prepare(season: GraduateSeason): Unit = {
    if (!season.persisted) {
      val code = getProject.id.toString + season.graduateIn.toString.replace("-", "")
      season.id = code.toLong
    }
  }
}
