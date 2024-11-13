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

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.doc.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.model.{Department, Project}
import org.openurp.base.resource.model.Classroom

import java.time.{Instant, LocalDate}

class ClassroomImportListener(entityDao: EntityDao, project: Project) extends ImportListener {
  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("classroom.code") foreach { code =>
      val query = OqlBuilder.from(classOf[Classroom], "c")
      query.where("c.code =:code and c.school=:school", code, project.school)
      entityDao.search(query).foreach { cl =>
        transfer.current = cl
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val classroom = transfer.current.asInstanceOf[Classroom]
    classroom.projects.add(project)
    classroom.updatedAt = Instant.now
    classroom.school = project.school
    if (null == classroom.beginOn) classroom.beginOn = LocalDate.now
    transfer.curData.get("departNames") foreach { names =>
      val departNames = names.toString
      if (Strings.isNotBlank(departNames)) {
        classroom.departs.clear()
        Strings.split(departNames, ",").foreach { name =>
          val query = OqlBuilder.from(classOf[Department], "d")
          query.where("d.name=:name", name)
          query.cacheable()
          val departs = entityDao.search(query)
          if (departs.isEmpty) {
            tr.addFailure("不存在的部门", name)
          } else {
            classroom.departs.addAll(departs)
          }
        }
      }
    }
    entityDao.saveOrUpdate(classroom)
  }
}
