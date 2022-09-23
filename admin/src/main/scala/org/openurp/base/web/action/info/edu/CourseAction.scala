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

package org.openurp.base.web.action.info.edu

import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.{mapping, param}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.EntityAction
import org.openurp.base.edu.model.Course
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class CourseAction extends ActionSupport with EntityAction[Course] with ProjectSupport {

  def index(): View = {
    val project = getProject
    val dQuery = OqlBuilder.from(classOf[Course].getName, "c")
    dQuery.where("c.project=:project", project)
    dQuery.where("c.endOn is null or c.endOn > :now", LocalDate.now)
    dQuery.select("c.department.id,c.department.name,count(*)")
    dQuery.groupBy("c.department.id,c.department.code,c.department.name")
    dQuery.orderBy("c.department.code")
    put("departStat", entityDao.search(dQuery))

    val ctQuery = OqlBuilder.from(classOf[Course].getName, "c")
    ctQuery.where("c.project=:project", project)
    ctQuery.where("c.endOn is null or c.endOn > :now", LocalDate.now)
    ctQuery.select("c.courseType.id,c.courseType.name,count(*)")
    ctQuery.groupBy("c.courseType.id,c.courseType.code,c.courseType.name")
    ctQuery.orderBy("c.courseType.code")
    put("typeStat", entityDao.search(ctQuery))

    val ccQuery = OqlBuilder.from(classOf[Course].getName, "c")
    ccQuery.where("c.project=:project", project)
    ccQuery.where("c.endOn is null or c.endOn > :now", LocalDate.now)
    ccQuery.select("c.nature.id,c.nature.name,count(*)")
    ccQuery.groupBy("c.nature.id,c.nature.code,c.nature.name")
    ccQuery.orderBy("c.nature.code")
    put("natureStat", entityDao.search(ccQuery))

    forward()
  }

  def search(): View = {
    val project = getProject
    val query = getQueryBuilder
    query.where("course.project=:project",project)
    get("q") foreach { q =>
      query.where("course.code like :q or course.name like :q", s"%${q.trim}%")
    }
    query.where("course.endOn is null or course.endOn > :now", LocalDate.now)
    query.orderBy("course.code")
    put("courses", entityDao.search(query))
    forward()
  }

  @mapping(value = "{id}")
  def info(@param("id") id: String): View = {
    put(simpleEntityName, entityDao.get(classOf[Course],id.toLong))
    forward()
  }
}
