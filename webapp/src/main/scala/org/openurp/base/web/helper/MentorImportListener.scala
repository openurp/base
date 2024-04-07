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
import org.openurp.base.hr.model.{Mentor, Staff}
import org.openurp.base.model.Project

class MentorImportListener(entityDao: EntityDao, project: Project, urpUserHelper: UrpUserHelper) extends ImportListener {
  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("staff.code") foreach { code =>
      val staffs = entityDao.findBy(classOf[Staff], "code" -> code, "school" -> project.school)
      if (staffs.size == 1) {
        val mentor =
          entityDao.findBy(classOf[Mentor], "staff" -> staffs.head).headOption match {
            case None =>
              val m = new Mentor()
              m.staff = staffs.head
              m
            case Some(m) => m
          }
        transfer.current = mentor
      } else {
        tr.addFailure("错误的工号", code)
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val mentor = transfer.current.asInstanceOf[Mentor]
    if (null != mentor.staff) {
      val staff = mentor.staff
      mentor.name = staff.name

      if (!mentor.persisted) mentor.id = staff.id
      if (null == mentor.beginOn) mentor.beginOn = staff.beginOn
      mentor.projects += project
      entityDao.saveOrUpdate(mentor)
    }
  }
}
