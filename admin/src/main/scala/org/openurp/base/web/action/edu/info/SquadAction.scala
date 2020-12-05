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
import org.beangle.webmvc.api.view.View
import org.openurp.base.edu.model.Squad

class SquadAction extends ProjectRestfulAction[Squad] {

  override def getQueryBuilder: OqlBuilder[Squad] = {
    val builder = super.getQueryBuilder
    builder.where("squad.endOn is null or squad.endOn > :now", LocalDate.now)
  }

  def index(): View = {
    val dQuery = OqlBuilder.from(classOf[Squad].getName, "t")
    dQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    dQuery.select("t.department.id,t.department.name,count(*)")
    dQuery.groupBy("t.department.id,t.department.code,t.department.name")
    dQuery.orderBy("t.department.code")
    put("departStat", entityDao.search(dQuery))

    val ctQuery = OqlBuilder.from(classOf[Squad].getName, "t")
    ctQuery.where("t.endOn is null or t.endOn > :now", LocalDate.now)
    ctQuery.select("t.grade,count(*)")
    ctQuery.groupBy("t.grade")
    ctQuery.orderBy("t.grade")
    put("gradeStat", entityDao.search(ctQuery))

    forward()
  }

  def search(): View = {
    val query = getQueryBuilder
    get("q") foreach { q =>
      query.where("squad.code like :q or squad.name like :q", s"%${q.trim}%")
    }
    query.orderBy("squad.code")
    put("squads", entityDao.search(query))
    forward()
  }

}
