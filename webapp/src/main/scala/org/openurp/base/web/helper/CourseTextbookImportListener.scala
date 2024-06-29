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
import org.openurp.base.edu.model.{CourseTextbook, Textbook}
import org.openurp.base.model.Project

import java.time.LocalDate

class CourseTextbookImportListener(entityDao: EntityDao, project: Project) extends ImportListener {

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("courseTextbook.textbook.isbn") foreach { isbn =>
      entityDao.findBy(classOf[Textbook], "isbn", isbn).headOption match
        case None => tr.addFailure("没有对应ISBN的教材", isbn)
        case Some(t) =>
          transfer.curData.put("courseTextbook.textbook", t)
          transfer.curData.remove("courseTextbook.textbook.isbn")
    }
    super.onItemStart(tr)
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val cb = transfer.current.asInstanceOf[CourseTextbook]
    if (null != cb.course && null != cb.textbook && cb.textbook.persisted) {
      if null == cb.beginOn then cb.beginOn = LocalDate.now
      entityDao.saveOrUpdate(cb)
    } else {
      if (null == cb.course) tr.addFailure("没有对应的课程", transfer.curData.get("courseTextbook.course.code"))
    }
  }
}
