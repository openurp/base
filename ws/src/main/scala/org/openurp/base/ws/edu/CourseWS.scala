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

package org.openurp.base.ws.edu

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.jsonapi.JsonAPI
import org.beangle.web.action.annotation.response
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.Course

class CourseWS extends ActionSupport with EntityAction[Course] {

  @response
  def index(): JsonAPI.Json = {
    val projectId = getInt("project", 0)
    val query = OqlBuilder.from(classOf[Course])
    query.where("course.project.id=:projectId", projectId)
    QueryHelper.populate(query).limit(query).sort(query)
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("course.name like :c or course.code like :c", c)
    }
    val courses = entityDao.search(query)
    val context = JsonAPI.context(ActionContext.current.params)
    context.mkJson(courses, "id", "code", "name", "enName")
  }
}
