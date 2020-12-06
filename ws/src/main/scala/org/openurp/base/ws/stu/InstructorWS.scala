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
package org.openurp.base.ws.stu

import org.beangle.commons.collection.Properties
import org.beangle.commons.collection.page.PageLimit
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.response
import org.beangle.webmvc.entity.action.EntityAction
import org.beangle.webmvc.entity.helper.QueryHelper.{PageParam, PageSizeParam}
import org.openurp.base.edu.model.Semester
import org.openurp.base.stu.model.Instructor

class InstructorWS extends ActionSupport with EntityAction[Semester] {

  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Instructor], "instructor")
    populateConditions(query)
    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("instructor.user.name like :c or instructor.user.code like :c", c)
    }
    entityDao.search(query).map { t =>
      val instructor = new Properties(t, "id")
      val user = new Properties(t.user, "id", "code", "name")
      user.add("department", t.user.department, "id", "code", "name")
      instructor.put("user", user)
      instructor
    }
  }

}
