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

package org.openurp.base.web.action.info.hr

import org.beangle.commons.bean.orderings.PropertyOrdering
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.annotation.{mapping, param}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.view.View
import org.openurp.base.hr.model.{StaffTitle, Teacher, TutorJournal, TutorMajor}
import org.openurp.base.std.model.Student
import org.openurp.code.job.model.TutorType
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class TeacherAction extends ActionSupport, EntityAction[Teacher], ProjectSupport {

  var entityDao: EntityDao = _

  override def getQueryBuilder: OqlBuilder[Teacher] = {
    val builder = super.getQueryBuilder
    builder.where("teacher.endOn is null or teacher.endOn > :now", LocalDate.now)
  }

  def index(): View = {
    val dQuery1 = OqlBuilder.from(classOf[Teacher].getName, "t")
    dQuery1.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    dQuery1.where("t.department.teaching=true")
    dQuery1.select("t.department.id,t.department.name,count(*)")
    dQuery1.groupBy("t.department.id,t.department.code,t.department.name")
    dQuery1.orderBy("t.department.code")
    put("departStat", entityDao.search(dQuery1))

    val dQuery2 = OqlBuilder.from(classOf[Teacher].getName, "t")
    dQuery2.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    dQuery2.where("t.department.teaching=false")
    dQuery2.select("t.department.id,t.department.name,count(*)")
    dQuery2.groupBy("t.department.id,t.department.code,t.department.name")
    dQuery2.orderBy("t.department.code")
    put("otherDepartStat", entityDao.search(dQuery2))

    val ctQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    ctQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    ctQuery.select("t.staff.staffType.id,t.staff.staffType.name,count(*)")
    ctQuery.groupBy("t.staff.staffType.id,t.staff.staffType.code,t.staff.staffType.name")
    ctQuery.orderBy("t.staff.staffType.code")
    put("typeStat", entityDao.search(ctQuery).sorted(PropertyOrdering.by("[2] desc,[1]")))

    val categoryQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    categoryQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    categoryQuery.select("sum(case when t.staff.parttime=true then 1 else 0 end)," +
      "sum(case when t.staff.external=true then 1 else 0 end)," +
      "sum(case when t.staff.formalHr=true then 1 else 0 end),count(*)")
    put("categoryStats", entityDao.search(categoryQuery).head)

    val titleQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    titleQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    titleQuery.select("t.staff.title.id,t.staff.title.name,count(*)")
    titleQuery.groupBy("t.staff.title.id,t.staff.title.code,t.staff.title.name")
    titleQuery.orderBy("t.staff.title.code")
    put("titleStat", entityDao.search(titleQuery).sorted(PropertyOrdering.by("[2] desc,[1]")))
    forward()
  }

  def search(): View = {
    val query = getQueryBuilder
    get("q") foreach { q =>
      query.where("teacher.staff.code like :q or teacher.staff.name like :q", s"%${q.trim}%")
    }
    query.orderBy("teacher.staff.code")
    put("tutorTypes", codeService.get(classOf[TutorType]))
    put("teachers", entityDao.search(query))
    forward()
  }

  @mapping(value = "{id}")
  def info(@param("id") id: String): View = {
    val teacher = entityDao.get(classOf[Teacher], id.toLong)
    val staff = teacher.staff
    val titles = entityDao.findBy(classOf[StaffTitle], "staff", staff)
    put("staff", teacher.staff)
    put("staffTitles", titles)

    val majors = entityDao.findBy(classOf[TutorMajor], "staff", staff)
    val appointOn = entityDao.findBy(classOf[TutorJournal], "staff", staff).map(x => (x.tutorType, x.beginOn)).toMap
    put("majors", majors)
    put("appointOn", appointOn)

    val stdQuery = OqlBuilder.from(classOf[Student], "std")
    stdQuery.where("std.tutor=:me", teacher)
    stdQuery.where(":today between std.beginOn and std.endOn", LocalDate.now)
    stdQuery.orderBy("std.code")
    put("students", entityDao.search(stdQuery))
    forward()
  }
}
