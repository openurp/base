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
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.edu.model.TeachingOffice
import org.openurp.base.model.Project

import java.time.{Instant, LocalDate}

/**
 * @author chaostone
 */
class TeachingOfficeImportListener(project: Project, entityDao: EntityDao) extends ImportListener {
  override def onItemFinish(tr: ImportResult): Unit = {
    val tg = tr.transfer.current.asInstanceOf[TeachingOffice]
    //    tg.project = project
    tg.updatedAt = Instant.now
    if (null == tg.beginOn) {
      tg.beginOn = LocalDate.now
    }
    entityDao.saveOrUpdate(tg)
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
    val query = OqlBuilder.from(classOf[TeachingOffice], "tg")
    //FIXME project not ready
    //    query.where("tg.project=:project", project)
    query.where("tg.code=:code", tr.transfer.curData.getOrElse("teachingOffice.code", ""))

    val groups = entityDao.search(query)
    if (groups.size == 1) {
      tr.transfer.current = groups.head
    }
  }

}
