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

import org.beangle.commons.bean.orderings.CollatorOrdering
import org.beangle.commons.collection.Collections
import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.json.JsonAPI
import org.beangle.data.json.JsonAPI.Context
import org.beangle.she.webmvc.QueryHelper
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.ActionSupport
import org.openurp.base.edu.model.Major
import org.openurp.base.hr.model.{Staff, Teacher, TutorMajor}
import org.openurp.base.model.Department
import org.openurp.base.std.model.{Grade, Student, Tutorship}
import org.openurp.code.CodeBean
import org.openurp.code.edu.model.{DegreeLevel, EducationLevel}
import org.openurp.code.job.model.ProfessionalGrade
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate
import scala.compiletime.uninitialized

/** /api/base/admin/hr/tutor-stat */
class TutorStatWS extends ActionSupport, ProjectSupport {

  var entityDao: EntityDao = uninitialized

  /** 按培养层次、方向列出导师。 */
  @response
  def direction(): Json = {
    val project = getProject
    val today = LocalDate.now
    val tms = entityDao.findBy(classOf[TutorMajor], "major.project", project).filter(_.staff.within(today))
    val datas = tms.flatMap { tm =>
      tm.directions.map(direction => (tm.level, tm.major, direction, tm.staff))
    }
    val grouped = datas.groupBy(_._1).map { d =>
      (d._1, d._2.groupBy(_._3).map(x => (x._1, x._2.map(_._4).distinct)))
    }
    val levels = grouped.map { case (level, directions) =>
      val item = new JsonObject()
      item.add("id", level.id)
      item.add("code", level.code)
      item.add("name", level.name)
      val dirs = directions.map { case (direction, staffs) =>
        val dir = new JsonObject()
        dir.add("id", direction.id)
        dir.add("code", direction.code)
        dir.add("name", direction.name)
        dir.add("major", named(direction.major.id, direction.major.code, direction.major.name))
        dir.add("tutors", staffs.map(staff => named(staff.id, staff.code, staff.name)))
        dir
      }
      item.add("directions", dirs)
      item
    }
    val rs = new JsonObject()
    rs.add("levels", levels)
    rs
  }

  /** 按专业汇总导师结构。 */
  @response
  def major(): Json = {
    val school = getProject.school
    val q = OqlBuilder.from[Array[Any]](classOf[Staff].getName + " staff," + classOf[TutorMajor].getName + " tm")
    q.where("staff.tutorType is not null")
    q.where("staff.school=:school", school)
    q.where("tm.staff=staff")
    q.where("staff.beginOn <= :today and (staff.endOn is null or staff.endOn >= :today)", LocalDate.now)
    q.select("staff.title.grade.id,staff.degreeLevel.id,max(tm.level.id)," +
      "staff.degreeAwardBy,staff.parttime," +
      "tm.major.id,staff.birthday,staff.id")
    q.groupBy("staff.title.grade.id,staff.degreeLevel.id," +
      "staff.degreeAwardBy,staff.parttime," +
      "tm.major.id,staff.birthday,staff.id")
    val today = LocalDate.now()
    val rows = entityDao.search(q).map { d =>
      val birthday = d(6).asInstanceOf[LocalDate]
      val age =
        if (null == birthday) "0"
        else {
          val years = birthday.until(today).getYears
          if (years <= 0) "0"
          else if (years <= 25) "1-25"
          else if (years <= 35) "26-35"
          else if (years <= 45) "36-45"
          else if (years <= 59) "46-59"
          else "60-150"
        }
      val outside = d(3).asInstanceOf[String]
      val row = new JsonObject()
      row.add("gradeId", d(0))
      row.add("degreeLevelId", d(1))
      row.add("eduLevelId", d(2))
      row.add("degreeAwardOutside", if (null == outside || outside == school.name) 0 else 1)
      row.add("parttime", d(4))
      row.add("majorId", d(5))
      row.add("age", age)
      row.add("count", 1)
      row
    }
    val rs = new JsonObject()
    rs.add("rows", rows)
    rs.add("grades", entityDao.getAll(classOf[ProfessionalGrade]).map(code => named(code.id, code.code, code.name)))
    rs.add("degreeLevels", entityDao.getAll(classOf[DegreeLevel]).map(code => named(code.id, code.code, code.name)))
    rs.add("eduLevels", entityDao.getAll(classOf[EducationLevel]).map(code => named(code.id, code.code, code.name)))
    rs.add("majors", entityDao.search(OqlBuilder.from(classOf[Major], "major").where("major.project.school=:school", school)).map(m => named(m.id, m.code, m.name)))
    rs
  }

  /** 按院系统计导师所带学生。 */
  @response
  def std(): Json = {
    val q = OqlBuilder.from[Array[Any]](classOf[Student].getName, "std")
    q.join("std.tutors", "st")
    q.where("st.tutorship=:ship", Tutorship.Major)
    q.where("std.registed = true and :today between std.beginOn and std.endOn", LocalDate.now)
    q.select("st.tutor.id,st.tutor.department.id,std.level.id,std.state.grade.id,std.graduationDeferred,count(*)")
    q.groupBy("st.tutor.id,st.tutor.department.id,std.level.id,std.state.grade.id,std.graduationDeferred")
    val teacherIds = Collections.newSet[Long]
    val departIds = Collections.newSet[Int]
    val rows = entityDao.search(q).map { d =>
      val teacherId = d(0).asInstanceOf[Long]
      val departId = d(1).asInstanceOf[Int]
      teacherIds.addOne(teacherId)
      departIds.addOne(departId)
      val row = new JsonObject()
      row.add("tutorId", teacherId)
      row.add("departId", departId)
      row.add("levelId", d(2))
      row.add("gradeId", d(3))
      row.add("graduationDeferred", d(4))
      row.add("count", d(5).asInstanceOf[Number].intValue())
      row
    }
    val teachers = entityDao.find(classOf[Teacher], teacherIds).sortBy(_.name)(using new CollatorOrdering(true))
    val rs = new JsonObject()
    rs.add("rows", rows)
    rs.add("teachers", teachers.map { t =>
      val item = named(t.id, t.staff.code, t.name)
      item.add("departmentId", t.department.id)
      item
    })
    rs.add("departments", entityDao.find(classOf[Department], departIds).sortBy(_.code).map(d => named(d.id, d.code, d.name)))
    rs
  }

  @response
  def stdList(): Json = {
    val q = OqlBuilder.from(classOf[Student], "std")
    q.where("exists(from std.tutors st where st.tutor.id=:tutorId and st.tutorship=:ship)", getLongId("tutor"), Tutorship.Major)
    q.where("std.registed = true and :today between std.beginOn and std.endOn", LocalDate.now)
    QueryHelper.populate(entityDao, q)
    q.orderBy("std.code desc")
    val students = entityDao.search(q)
    given ctx: Context = JsonAPI.context(ActionContext.current.params)
    ctx.include[CodeBean]("id", "code", "name")
    ctx.filters.include(classOf[Student], "*")
    JsonAPI.newJson(students.map(s => JsonAPI.create(s, "")))
  }

  private def named(id: Any, code: String, name: String): JsonObject = {
    val item = new JsonObject()
    item.add("id", id)
    item.add("code", code)
    item.add("name", name)
    item
  }
}
