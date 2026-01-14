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

package org.openurp.base.web.helper

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.transfer.importer.{EntityImportListener, ImportListener, ImportResult}
import org.openurp.base.edu.model.MajorDirection
import org.openurp.base.model.Project

import java.time.{Instant, LocalDate}

class DirectionImportListener(entityDao: EntityDao, project: Project) extends EntityImportListener {

  override def onItemStart(tr: ImportResult): Unit = {
    importer.datas.get("direction.code") foreach { code =>
      val query = OqlBuilder.from(classOf[MajorDirection], "c")
      query.where("c.code =:code and c.project=:project", code, project)
      val cs = entityDao.search(query)
      if (cs.nonEmpty) this.current = cs.head
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val direction = this.current[MajorDirection]
    direction.project = project
    direction.updatedAt = Instant.now
    if (null == direction.beginOn) {
      direction.beginOn = LocalDate.now
    }
    entityDao.saveOrUpdate(direction)
  }
}
