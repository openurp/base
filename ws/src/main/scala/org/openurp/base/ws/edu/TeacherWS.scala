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

import org.beangle.commons.collection.page.PageLimit
import org.beangle.commons.collection.{Order, Properties}
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.response
import org.beangle.webmvc.entity.action.EntityAction
import org.beangle.webmvc.entity.helper.QueryHelper.{PageParam, PageSizeParam}
import org.openurp.base.edu.model.{Project, Teacher}

class TeacherWS extends ActionSupport with EntityAction[Teacher] {
  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Teacher], "teacher")
    populateConditions(query)
    val p = new Project
    p.id = getInt("project").get
    query.where(":project in elements(teacher.projects)", p)
    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("teacher.user.name like :c or teacher.user.code like :c", c)
    }
    val orderStr = get(Order.OrderStr).getOrElse("teacher.user.name")
    query.orderBy(orderStr)
    entityDao.search(query).map { t =>
      val teacher = new Properties(t, "id")
      val user = new Properties(t.user, "id", "code", "name")
      user.add("department", t.user.department, "id", "code", "name")
      teacher.put("user", user)
      teacher
    }
  }

}
