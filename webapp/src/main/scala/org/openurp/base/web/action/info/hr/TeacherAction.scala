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

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.EntityAction
import org.openurp.base.hr.model.Teacher
import org.openurp.code.job.model.TutorType
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class TeacherAction extends ActionSupport with EntityAction[Teacher] with ProjectSupport {

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
    put("typeStat", entityDao.search(ctQuery))

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
    put("titleStat", entityDao.search(titleQuery))
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
}
