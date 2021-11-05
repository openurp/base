/*
 * Copyright (C) 2005, The OpenURP Software.
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
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.edu.model.{Project, Textbook}

import java.time.LocalDate

class TextbookImportListener(project: Project, entityDao: EntityDao) extends ImportListener {
  override def onStart(tr: ImportResult): Unit = {}

  override def onFinish(tr: ImportResult): Unit = {}

  override def onItemStart(tr: ImportResult): Unit = {
    val data = transfer.curData
    //按照isbn,name,author,publishedOn作为业务主键
    for (isbn <- data.get("textbook.isbn"); author <- data.get("textbook.author");
         name <- data.get("textbook.name"); p <- data.get("textbook.publishedOn")) {
      val publishedOnStr = p match {
        case s: String =>
          if (s.length < "xxxx-x-x".length) {
            s + "-01"
          } else {
            Strings.substringBeforeLast(s, "-") + "-01"
          }
        case d: java.util.Date => new java.sql.Date(d.getTime).toLocalDate.toString
        case _ => p.toString
      }
      try {
        val publishedOn = LocalDate.parse(publishedOnStr)
        data.put("textbook.publishedOn", publishedOn)
        val query = OqlBuilder.from(classOf[Textbook], "t")
          .where("t.isbn=:isbn", isbn.toString.trim())
        val cs = entityDao.search(query)
        if (cs.nonEmpty) {
          transfer.current = cs.head
        }
      } catch {
        case e: Throwable => tr.addFailure("错误的出版日期格式", p)
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val book = transfer.current.asInstanceOf[Textbook]
    book.project = project
    book.beginOn = LocalDate.now
    entityDao.saveOrUpdate(book)
  }
}
