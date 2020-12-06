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
package org.openurp.base.ws.edu

import java.time.LocalDate

import org.beangle.commons.collection.Properties
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.response
import org.beangle.webmvc.entity.action.EntityAction
import org.openurp.base.edu.model.{Project, Semester}

class SemesterWS extends ActionSupport with EntityAction[Semester]  {

  @response
  def index: Seq[Properties] = {
    val project = entityDao.get(classOf[Project], getInt("project").get)
    getSemesters(project)
  }

  private def getSemesters(project: Project): Seq[Properties] = {
    val now = LocalDate.now
    val calendar = project.calendars.find(x => !x.beginOn.isAfter(now) && x.endOn.forall(!now.isAfter(_)))
    calendar match {
      case Some(c) =>
        val builder = OqlBuilder.from(classOf[Semester], "s").where("s.calendar =:calendar", c)
        builder.orderBy("s.beginOn").cacheable(true)
        entityDao.search(builder).map(new Properties(_, "id", "name", "code", "schoolYear"))
      case None => List.empty
    }
  }
}
