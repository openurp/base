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
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.openurp.base.edu.model.{Course, CourseProfile}

class CourseProfileWS extends ActionSupport, EntityAction[CourseProfile] {

  var entityDao: EntityDao = _

  @mapping("{id}")
  @response
  def index(@param("id") id: String): Properties = {
    val builder = OqlBuilder.from(classOf[CourseProfile], "tp")
    builder.where("tp.course.id=:courseId", id.toLong)
    entityDao.search(builder).headOption match {
      case Some(e) => new Properties(e, "id", "description", "enDescription")
      case None => new Properties()
    }
  }
}
