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
package org.openurp.base.web.action.edu.info

import java.time.LocalDate

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.mapping
import org.beangle.webmvc.api.view.View
import org.openurp.base.edu.model.Major

class MajorAction extends ProjectRestfulAction[Major] {

  def index(): View = {
    val project = getProject
    val query = OqlBuilder.from(classOf[Major], "major")
    query.where("major.project=:project", project)
    query.where("major.endOn is null or major.endOn > :today", LocalDate.now)
    query.orderBy("major.code")
    put("majors", entityDao.search(query))
    forward()
  }

  @mapping("{id}")
  def info(id: String): View = {
    val major = entityDao.get(classOf[Major], id.toLong)
    put("major", major)
    forward()
  }
}
