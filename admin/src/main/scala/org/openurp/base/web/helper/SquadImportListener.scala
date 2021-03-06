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
package org.openurp.base.edu.web.helper

import java.time.Instant

import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.edu.model.{Project, Squad}

/**
 * @author xinzhou
 */
class SquadImportListener(project: Project, entityDao: EntityDao) extends ImportListener {
  override def onItemFinish(tr: ImportResult): Unit = {
    val squad = tr.transfer.current.asInstanceOf[Squad]
    squad.project = project
    squad.updatedAt = Instant.now
    entityDao.saveOrUpdate(squad)
  }

  /**
   * 结束转换
   */
  override def onStart(tr: ImportResult): Unit = {
  }

  /**
   * 结束转换
   */
  override def onFinish(tr: ImportResult): Unit = {}

  /**
   * 开始转换单个项目
   */
  override def onItemStart(tr: ImportResult): Unit = {
    val squads = entityDao.findBy(classOf[Squad], "code", List(tr.transfer.curData.getOrElse("squad.code", "")))
    if (squads.size == 1) {
      tr.transfer.current = squads.head
    }
  }

}
