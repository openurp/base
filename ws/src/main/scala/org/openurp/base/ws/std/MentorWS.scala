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

import org.beangle.commons.collection.Properties
import org.beangle.commons.collection.page.PageLimit
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.response
import org.beangle.web.action.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper.{PageParam, PageSizeParam}
import org.openurp.base.std.model.Mentor

class MentorWS extends ActionSupport with EntityAction[Mentor] {

  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Mentor], "mentor")
    populateConditions(query)
    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("mentor.user.name like :c or mentor.user.code like :c", c)
    }
    entityDao.search(query).map { t =>
      val mentor = new Properties(t, "id")
      val user = new Properties(t.user, "id", "code", "name")
      user.add("department", t.user.department, "id", "code", "name")
      mentor.put("user", user)
      mentor
    }
  }

}
