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

import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.model.{Project, Staff}

import java.time.{Instant, LocalDate}

class StaffImportListener(entityDao: EntityDao, project: Project, urpUserHelper: UrpUserHelper) extends ImportListener {

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("staff.code") foreach { code =>
      val cs = entityDao.findBy(classOf[Staff], "code", List(code))
      if (cs.nonEmpty) transfer.current = cs.head
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val staff = transfer.current.asInstanceOf[Staff]
    staff.school = project.school
    staff.updatedAt = Instant.now
    if (null == staff.beginOn) staff.beginOn = LocalDate.now.minusDays(1)
    entityDao.saveOrUpdate(staff)
    urpUserHelper.createStaffUser(staff, None)
  }
}
