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
import org.beangle.commons.json.JsonObject
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.she.webmvc.{EntityAction, QueryHelper}
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.ActionSupport
import org.openurp.base.edu.model.Textbook
import org.openurp.base.service.impl.TextbookHelper

import java.time.LocalDate

class TextbookWS extends ActionSupport, EntityAction[Textbook] {
  var entityDao: EntityDao = _

  @response
  def index(): Iterable[Properties] = {
    val projectId = getIntId("project")
    val query = OqlBuilder.from(classOf[Textbook])
    query.where("textbook.project.id=:projectId", projectId)
    QueryHelper.populate(entityDao, query).limit(query).sort(query)
    get("q") foreach { q =>
      val c = s"%${q}%"
      query.where("textbook.name like :c or textbook.isbn like :c", c)
    }
    val date = getDate("activeOn").getOrElse(LocalDate.now)
    query.where("textbook.beginOn <= :date and (textbook.endOn is null or textbook.endOn >= :date)", date)

    val books = entityDao.search(query)
    books.map(x => new Properties(x, "id", "title"))
  }

  @response
  def queryByIsbn(): JsonObject = {
    val isbn = get("isbn").orNull
    TextbookHelper.fetchByIsbn(isbn)
  }
}
