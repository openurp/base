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
import org.beangle.she.webmvc.EntityAction
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.ActionSupport
import org.openurp.base.edu.model.MajorDirection

class DirectionWS extends ActionSupport, EntityAction[MajorDirection] {

  var entityDao: EntityDao = _

  @response
  def index(): Iterable[Properties] = {
    val projectId = getInt("project", 0)
    val query = OqlBuilder.from(classOf[MajorDirection], "direction")
    query.where("direction.project.id=:projectId", projectId)
    val directions = entityDao.search(query)
    directions.map(x => new Properties(x, "id", "code", "name", "enName"))
  }
}
