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

package org.openurp.base.ws.std

import org.beangle.commons.collection.Order
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.jsonapi.JsonAPI
import org.beangle.data.jsonapi.JsonAPI.Context
import org.beangle.web.action.annotation.response
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.std.model.Squad
import org.openurp.code.CodeBean

class SquadWS extends ActionSupport with EntityAction[Squad] {
  var entityDao: EntityDao = _

  @response
  def index(): JsonAPI.Json = {
    val projectId = getInt("project", 0)
    val query = OqlBuilder.from(classOf[Squad])
    query.where("squad.project.id=:projectId", projectId)
    populateConditions(query)
    QueryHelper.sort(query)
    QueryHelper.limit(query)
    val squads = entityDao.search(query)

    given context: Context = JsonAPI.context(ActionContext.current.params)

    context.filters.include(classOf[Squad], "id", "code", "name", "enName")
    val resources = squads.map { g => JsonAPI.create(g, "") }
    JsonAPI.newJson(resources)
  }

}
