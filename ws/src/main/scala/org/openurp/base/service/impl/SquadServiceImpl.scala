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
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.openurp.base.std.model.Squad

import java.time.{Instant, LocalDate}

class SquadServiceImpl extends Logging {

  var entityDao: EntityDao = _

  def autoStatStdCount(): Int = {
    val today=LocalDate.now()

    val query = OqlBuilder.from(classOf[Squad], "s")
    query.where("s.endOn >= :today", today)
    val squads = entityDao.search(query)
    var updated = 0
    squads foreach { squad =>
      val examinDay = if (squad.endOn.isBefore(today)) squad.endOn.minusDays(30) else today
      val newCount = squad.stdStates.filter(x => x.within(examinDay) && x.inschool).size
      if (newCount != squad.stdCount) {
        squad.stdCount = newCount
        updated += 1
        squad.updatedAt = Instant.now
      }
    }
    entityDao.saveOrUpdate(squads)
    if updated > 0 then logger.info(s"auto stat ${updated} squads std count.")
    updated
  }
}
