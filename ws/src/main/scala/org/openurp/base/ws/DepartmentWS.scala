/*
 * Copyright (C) 2005, The OpenURP Software.
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

import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.response
import org.openurp.base.model.{Department, School}

class SchoolWS extends RestfulService[School]

class DepartmentWS extends RestfulService[Department] {

  @response
  override def index(): Any = {
    put("properties", List(classOf[Department] -> List("id", "code", "name", "shortName")))
    getInt("page") match {
      case Some(p) => entityDao.search(getQueryBuilder)
      case None => entityDao.search(getQueryBuilder.limit(null))
    }
  }

  @response
  def teaching(): Seq[Department] = {
    put("properties", List(classOf[Department] -> List("id", "code", "name", "shortName")))
    entityDao.search(getQueryBuilder.limit(null).where("department.teaching=true"))
  }

  override protected def getQueryBuilder: OqlBuilder[Department] = {
    super.getQueryBuilder.orderBy("department.code")
  }
}
