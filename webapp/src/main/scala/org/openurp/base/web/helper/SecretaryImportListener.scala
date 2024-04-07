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
import org.beangle.doc.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.hr.model.{Secretary, Staff}
import org.openurp.base.model.Project

import java.time.LocalDate

class SecretaryImportListener(entityDao: EntityDao, project: Project, urpUserHelper: UrpUserHelper) extends ImportListener {
  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("staff.code") foreach { code =>
      val staffs = entityDao.findBy(classOf[Staff], "code" -> code, "school" -> project.school)
      if (staffs.size == 1) {
        val secretary =
          entityDao.find(classOf[Secretary], staffs.head.id) match {
            case None =>
              val m = new Secretary()
              m.staff = staffs.head
              m
            case Some(m) => m
          }
        transfer.current = secretary
      } else {
        tr.addFailure("错误的工号", code)
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val s = transfer.current.asInstanceOf[Secretary]
    if (null != s.staff) {
      if (null == s.beginOn) s.beginOn = LocalDate.now
      s.projects += project
      entityDao.saveOrUpdate(s)
    }
  }
}
