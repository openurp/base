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

package org.openurp.base.ws.hr

import org.beangle.commons.collection.page.PageLimit
import org.beangle.commons.collection.{Order, Properties}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper.{PageParam, PageSizeParam}
import org.openurp.base.hr.model.Teacher
import org.openurp.base.model.Project

class TeacherWS extends ActionSupport with EntityAction[Teacher] {
  var entityDao: EntityDao = _

  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Teacher], "teacher")
    populateConditions(query)
    getInt("projectId") foreach { pid =>
      val p = new Project
      p.id = pid
      query.where(":project in elements(teacher.projects)", p)
    }

    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("teacher.staff.name like :c or teacher.staff.code like :c", c)
    }
    getBoolean("isTutor") match {
      case None =>
      case Some(result) =>
        if (result) query.where("teacher.tutorType is not null")
        else query.where("teacher.tutorType is null")
    }
    val orderStr = get(Order.OrderStr).getOrElse("teacher.name")
    query.orderBy(orderStr)

    entityDao.search(query).map { t =>
      val teacher = new Properties(t, "id", "code", "name", "description")
      teacher.add("department", t.department, "id", "code", "name")
      teacher
    }
  }

}
