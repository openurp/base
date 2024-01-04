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

package org.openurp.base.ws.hr

import org.beangle.commons.collection.page.PageLimit
import org.beangle.commons.collection.{Order, Properties}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.annotation.response
import org.beangle.web.action.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper.{PageParam, PageSizeParam}
import org.openurp.base.hr.model.{Mentor, Staff, Teacher}
import org.openurp.base.model.User
import org.openurp.base.std.model.Student

class StaffWS extends ActionSupport with EntityAction[User] {
  var entityDao: EntityDao = _

  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Staff], "staff")
    populateConditions(query)
    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      val c = s"%$q%"
      query.where("staff.name like :c or staff.code like :c", c)
    }
    getBoolean("isMentor") foreach { isMentor =>
      query.where((if (isMentor) "" else "not ") + " exists(from " + classOf[Mentor].getName + "  t  where t.staff=staff)")
    }
    getBoolean("isTeacher") foreach { isTeacher =>
      query.where((if (isTeacher) "" else "not ") + " exists(from " + classOf[Teacher].getName + "  t  where t.staff=staff)")
    }
    getBoolean("isTutor") foreach { isTutor =>
      query.where((if (isTutor) "" else "not ") + " exists(from " + classOf[Teacher].getName + "  t  where t.staff=staff and t.tutorType is not null)")
    }
    val orderStr = get(Order.OrderStr).getOrElse("staff.name")
    query.orderBy(orderStr)
    entityDao.search(query).map { t =>
      new Properties(t, "id", "code", "name", "description")
    }
  }
}
