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
import org.beangle.transfer.importer.{EntityImportListener, ImportResult}
import org.openurp.base.hr.model.{Official, Staff}
import org.openurp.base.model.School

import java.time.LocalDate

class OfficialImportListener(entityDao: EntityDao, school: School) extends EntityImportListener {
  override def onItemStart(tr: ImportResult): Unit = {
    importer.datas.get("staff.code") foreach { code =>
      val staffs = entityDao.findBy(classOf[Staff], "code" -> code, "school" -> school)
      if (staffs.size == 1) {
        val official =
          entityDao.findBy(classOf[Official], "staff" -> staffs.head).headOption match {
            case None =>
              val m = new Official()
              m.staff = staffs.head
              m
            case Some(m) => m
          }
        this.current = official
      } else {
        tr.addFailure("错误的工号", code)
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val o = this.current[Official]
    if (null != o.staff) {
      if (null == o.beginOn) o.beginOn = LocalDate.now
      if (null == o.department) o.department = o.staff.department
      entityDao.saveOrUpdate(o)
    }
  }
}
