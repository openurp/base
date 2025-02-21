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

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.json.JsonAPI
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.{ActionSupport, MimeSupport}
import org.beangle.webmvc.support.action.EntityAction
import org.openurp.base.model.Campus

class CampusWS extends ActionSupport with EntityAction[Campus] with MimeSupport {

  var entityDao: EntityDao = _

  @response(cacheable = true)
  def index(): Any = {
    val query = OqlBuilder.from(classOf[Campus])
    query.orderBy("campus.code")
    query.cacheable()
    val grades = entityDao.search(query)

    val context = JsonAPI.context(ActionContext.current.params)
    context.mkJson(grades, "id", "code", "name")
  }
}
