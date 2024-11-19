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

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.jsonapi.JsonAPI
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.Major

class MajorWS extends ActionSupport with EntityAction[Major] {

  var entityDao: EntityDao = _

  @response
  def index(): JsonAPI.Json = {
    val projectId = getInt("project", 0)
    val query = OqlBuilder.from(classOf[Major])
    query.where("major.project.id=:projectId", projectId)
    QueryHelper.populate(query).limit(query).sort(query)
    val majors = entityDao.search(query)

    val context = JsonAPI.context(ActionContext.current.params)
    context.mkJson(majors, "id", "code", "name", "enName")
  }
}
