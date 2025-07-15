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
import org.openurp.base.model.Project
import org.openurp.base.resource.model.{Classroom, Laboratory}

import java.time.{Instant, LocalDate}

class LaboratoryImportListener(entityDao: EntityDao, project: Project) extends ImportListener {
  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("laboratory.code") foreach { code =>
      val query = OqlBuilder.from(classOf[Laboratory], "c")
      query.where("c.code =:code and c.school=:school", code, project.school)
      entityDao.search(query).foreach { cl =>
        transfer.current = cl
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val laboratory = transfer.current.asInstanceOf[Laboratory]
    laboratory.updatedAt = Instant.now
    laboratory.school = project.school
    var classroomCode: String = null
    transfer.curData.get("room.code") foreach { roomCode =>
      val q = OqlBuilder.from(classOf[Classroom], "r")
      q.where(":project in elements(r.projects)", project)
      q.where("r.code=:code or r.name=:code", roomCode)
      val rooms = entityDao.search(q)
      laboratory.room = rooms.headOption
      classroomCode = roomCode.toString
    }
    if (null != classroomCode && (null == laboratory.room || laboratory.room.isEmpty)) {
      tr.addFailure(s"${laboratory.name} 匹配不到 ${classroomCode}", classroomCode)
    }
    if (null == laboratory.beginOn) laboratory.beginOn = LocalDate.now
    entityDao.saveOrUpdate(laboratory)
  }
}
