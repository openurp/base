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
import org.openurp.base.edu.model.{Course, CourseLevel}
import org.openurp.base.model.Project
import org.openurp.code.edu.model.GradingMode

import java.time.{Instant, LocalDate}

class CourseImportListener(entityDao: EntityDao, project: Project) extends ImportListener {

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("course.code") foreach { code =>
      val query = OqlBuilder.from(classOf[Course], "c")
      query.where("c.code =:code and c.project=:project", code, project)
      val cs = entityDao.search(query)
      if (cs.nonEmpty) transfer.current = cs.head
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val course = transfer.current.asInstanceOf[Course]
    course.project = project
    course.updatedAt = Instant.now
    if (course.levels.isEmpty) {
      val cls = project.levels.map { x => new CourseLevel(course, x) }
      course.levels.addAll(cls)
    }
    if (null == course.gradingMode) {
      course.gradingMode = new GradingMode(GradingMode.Percent)
    }
    if (null == course.beginOn) {
      course.beginOn = LocalDate.now
    }
    entityDao.saveOrUpdate(course)
  }
}
