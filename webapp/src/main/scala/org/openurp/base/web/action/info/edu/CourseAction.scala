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

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.annotation.{mapping, param}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.Course
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class CourseAction extends ActionSupport with EntityAction[Course] with ProjectSupport {
  var entityDao: EntityDao = _

  def index(): View = {
    val project = getProject
    val dQuery1 = OqlBuilder.from(classOf[Course].getName, "c")
    dQuery1.where("c.project=:project", project)
    dQuery1.where("c.department.teaching=true")
    dQuery1.where("c.endOn is null or c.endOn > :now", LocalDate.now)
    dQuery1.select("c.department.id,c.department.name,count(*)")
    dQuery1.groupBy("c.department.id,c.department.code,c.department.name")
    dQuery1.orderBy("c.department.code")
    put("departStat", entityDao.search(dQuery1))

    val dQuery2 = OqlBuilder.from(classOf[Course].getName, "c")
    dQuery2.where("c.project=:project", project)
    dQuery2.where("c.department.teaching=false")
    dQuery2.where("c.endOn is null or c.endOn > :now", LocalDate.now)
    dQuery2.select("c.department.id,c.department.name,count(*)")
    dQuery2.groupBy("c.department.id,c.department.code,c.department.name")
    dQuery2.orderBy("c.department.code")
    put("otherDepartStat", entityDao.search(dQuery2))

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
    import query.given
    query.where(_.project.equal(project))
    get("q") foreach { q =>
      val qv = s"%${q.trim}%"
      query.where(e => e.code.like(qv) or e.name.like(qv))
    }
    query.where(e => e.endOn.isNull or e.endOn.gt(LocalDate.now))
    query.orderBy("course.code")
    put("courses", entityDao.search(query))
    forward()
  }

  @mapping(value = "{id}")
  def info(@param("id") id: String): View = {
    put(simpleEntityName, entityDao.get(classOf[Course], id.toLong))
    forward()
  }
}
