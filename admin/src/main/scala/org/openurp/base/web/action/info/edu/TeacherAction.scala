/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.info.edu

import java.time.LocalDate

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.EntityAction
import org.openurp.base.edu.model.Teacher
import org.openurp.boot.edu.helper.ProjectSupport

class TeacherAction extends ActionSupport with EntityAction[Teacher] with ProjectSupport {

  override def getQueryBuilder: OqlBuilder[Teacher] = {
    val builder = super.getQueryBuilder
    builder.where("teacher.endOn is null or teacher.endOn > :now", LocalDate.now)
  }

  def index(): View = {
    val dQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    dQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    dQuery.select("t.user.department.id,t.user.department.name,count(*)")
    dQuery.groupBy("t.user.department.id,t.user.department.code,t.user.department.name")
    dQuery.orderBy("t.user.department.code")
    put("departStat", entityDao.search(dQuery))

    val ctQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    ctQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    ctQuery.select("t.teacherType.id,t.teacherType.name,count(*)")
    ctQuery.groupBy("t.teacherType.id,t.teacherType.code,t.teacherType.name")
    ctQuery.orderBy("t.teacherType.code")
    put("typeStat", entityDao.search(ctQuery))

    val categoryQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    categoryQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    categoryQuery.select("sum(case when t.parttime=true then 1 else 0 end)," +
      "sum(case when t.retired=true then 1 else 0 end)," +
      "sum(case when t.formalHr=true then 1 else 0 end),count(*)")
    put("categoryStats", entityDao.search(categoryQuery).head)

    val titleQuery = OqlBuilder.from(classOf[Teacher].getName, "t")
    titleQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    titleQuery.select("t.title.id,t.title.name,count(*)")
    titleQuery.groupBy("t.title.id,t.title.code,t.title.name")
    titleQuery.orderBy("t.title.code")
    put("titleStat", entityDao.search(titleQuery))
    forward()
  }

  def search(): View = {
    val query = getQueryBuilder
    get("q") foreach { q =>
      query.where("teacher.user.code like :q or teacher.user.name like :q", s"%${q.trim}%")
    }
    query.orderBy("teacher.user.code")
    put("teachers", entityDao.search(query))
    forward()
  }
}
