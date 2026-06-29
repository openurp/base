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

import org.beangle.commons.collection.Properties
import org.beangle.commons.lang.Strings
import org.beangle.commons.net.http.HttpUtils
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.she.webmvc.{EntityAction, QueryHelper}
import org.beangle.web.servlet.url.UrlBuilder
import org.beangle.webmvc.annotation.{param, response}
import org.beangle.webmvc.support.ActionSupport
import org.openurp.api.URPTool
import org.openurp.base.edu.model.Course

import java.time.LocalDate

class CourseWS extends ActionSupport, EntityAction[Course] {
  var entityDao: EntityDao = _

  @response
  def index(): Iterable[Properties] = {
    val projectId = getIntId("project")
    val query = OqlBuilder.from(classOf[Course])
    query.where("course.project.id=:projectId", projectId)
    QueryHelper.populate(entityDao, query).limit(query).sort(query)
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("course.name like :c or course.code like :c", c)
    }
    val date = getDate("activeOn").getOrElse(LocalDate.now)
    query.where("course.beginOn <= :date and (course.endOn is null or course.endOn >= :date)", date)

    val courses = entityDao.search(query)
    courses.map(x => new Properties(x, "id", "code", "name", "enName", "defaultCredits", "description"))
  }

  @response
  def en(@param("q") q: String): String = {
    if Strings.isEmpty(q) then "Param q is needed"
    else if q.length > 200 then "Cannot handle Chinese characters exceeding 200"
    else
      val text = "《" + q + "》"
      HttpUtils.get(URPTool.url + "/translate/en?" + UrlBuilder.encodeParams(Map("q" -> text))).getText.trim()
  }
}
