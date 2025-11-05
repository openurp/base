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

package org.openurp.base.service.impl

import org.beangle.commons.logging.Logging
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.orm.hibernate.AbstractDaoTask
import org.openurp.base.std.model.Squad
import org.openurp.base.std.service.SquadService

import java.time.LocalDate

class SquadStdCountUpdater extends AbstractDaoTask, Logging {
  var squadService: SquadService = _

  override def execute(): Unit = {
    val today = LocalDate.now()

    val query = OqlBuilder.from(classOf[Squad], "s")
    query.where("s.endOn >= :today", today)
    val squads = entityDao.search(query)
    val updated = squadService.statStdCount(squads)
    if updated > 0 then logger.info(s"auto stat ${updated} squads stdcount.")
  }
}
