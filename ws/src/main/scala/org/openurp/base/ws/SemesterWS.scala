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

package org.openurp.base.ws

import org.beangle.commons.collection.Properties
import org.beangle.commons.lang.Numbers
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.openurp.base.model.{Project, Semester}

class SemesterWS extends ActionSupport with EntityAction[Semester] {
  var entityDao: EntityDao = _

  @response(cacheable = true)
  @mapping("{project}")
  def index(@param("project") projectId: String): Seq[Properties] = {
    if Numbers.isDigits(projectId) then
      entityDao.find(classOf[Project], projectId.toInt) match {
        case Some(project) => getSemesters(project, get("filter", ""))
        case None => Seq.empty
      }
    else
      Seq.empty
  }

  private def getSemesters(project: Project, filter: String): Seq[Properties] = {
    val builder = OqlBuilder.from(classOf[Semester], "s").where("s.calendar =:calendar", project.calendar)
    filter match {
      case "active" => builder.where("s.year.archived=false")
      case "archived" => builder.where("s.year.archived=true")
      case _ =>
    }
    builder.orderBy("s.beginOn").cacheable(true)
    entityDao.search(builder).map(new Properties(_, "id", "name", "code", "schoolYear"))
  }
}
