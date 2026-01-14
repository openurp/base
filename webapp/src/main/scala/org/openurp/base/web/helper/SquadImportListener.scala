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
import org.openurp.base.model.Project
import org.openurp.base.std.model.{Grade, Squad}

import java.time.Instant

/**
 * @author xinzhou
 */
class SquadImportListener(project: Project, entityDao: EntityDao) extends EntityImportListener {

  /**
   * 开始转换单个项目
   */
  override def onItemStart(tr: ImportResult): Unit = {
    val squads = entityDao.findBy(classOf[Squad], "code", List(tr.importer.datas.getOrElse("squad.code", "")))
    if (squads.size == 1) {
      this.current = squads.head
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val squad = this.current[Squad]
    squad.project = project
    tr.importer.datas.get("grade.name") foreach { grade =>
      val query = OqlBuilder.from(classOf[Grade], "g")
      query.where("g.project=:project", project)
      query.where("g.name=:name", grade)
      entityDao.search(query).headOption match {
        case Some(g) => squad.grade = g
        case None => tr.addFailure("不存在的年级", grade)
      }
    }
    //FIXME missing full final check
    if (null != squad.grade && null != squad.level) {
      squad.updatedAt = Instant.now
      entityDao.saveOrUpdate(squad)
    } else {
      tr.addFailure("层次或年级缺失", squad.code)
    }
  }
}
