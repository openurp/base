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
import org.beangle.doc.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.hr.model.{Staff, Teacher}
import org.openurp.base.model.{Project, User}

class TeacherImportListener(entityDao: EntityDao, project: Project) extends ImportListener {
  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("teacher.staff.code") foreach { code =>
      val query = OqlBuilder.from(classOf[Teacher], "t")
      query.where("t.staff.code=:code", code)
      query.where("t.staff.school=:school", project.school)
      val cs = entityDao.search(query)
      if (cs.nonEmpty) {
        transfer.current = cs.head
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val teacher = transfer.current.asInstanceOf[Teacher]
    if (null != teacher.staff) {
      val staff = teacher.staff
      teacher.name = staff.name

      if (null == teacher.department) teacher.department = staff.department
      if (!teacher.persisted) teacher.id = staff.id
      if (null == teacher.beginOn) teacher.beginOn = staff.beginOn
      teacher.projects += project
      entityDao.saveOrUpdate(teacher)
    }
  }
}
