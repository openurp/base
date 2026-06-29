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

import org.beangle.commons.collection.page.PageLimit
import org.beangle.commons.collection.{Order, Properties}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.she.webmvc.EntityAction
import org.beangle.she.webmvc.QueryHelper.{PageParam, PageSizeParam}
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.ActionSupport
import org.openurp.base.std.model.Student

class StudentWS extends ActionSupport with EntityAction[Student] {
  var entityDao: EntityDao = _

  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Student], "std")
    populateConditions(query)
    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      val c = s"%$q%"
      query.where("std.name like :c or std.code like :c", c)
    }
    query.where("std.project.id=:projectId",getIntId("project"))
    val orderStr = get(Order.OrderStr).getOrElse("std.code desc")
    query.orderBy(orderStr)
    entityDao.search(query).map(x => new Properties(x, "id", "code", "name", "description"))
  }

}
