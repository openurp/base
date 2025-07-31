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

package org.openurp.base.web.action.info.hr

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.annotation.{mapping, param}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.view.View
import org.openurp.base.hr.model.*
import org.openurp.base.std.model.Student
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class StaffAction extends ActionSupport, EntityAction[Teacher], ProjectSupport {

  var entityDao: EntityDao = _

  @mapping(value = "{id}")
  def info(@param("id") id: String): View = {
    val staff = entityDao.get(classOf[Staff], id.toLong)
    val titles = entityDao.findBy(classOf[StaffTitle], "staff", staff)
    put("staff", staff)
    put("staffTitles", titles)

    val majors = entityDao.findBy(classOf[TutorMajor], "staff", staff)
    val appointOn = entityDao.findBy(classOf[TutorJournal], "staff", staff).map(x => (x.tutorType, x.beginOn)).toMap
    put("majors", majors)
    put("appointOn", appointOn)

    val teacher = entityDao.findBy(classOf[Teacher], "staff", staff)
    if (teacher.nonEmpty) {
      val stdQuery = OqlBuilder.from(classOf[Student], "std")
      stdQuery.where("std.tutor=:me", teacher.head)
      stdQuery.where(":today between std.beginOn and std.endOn", LocalDate.now)
      stdQuery.orderBy("std.code")
      put("students", entityDao.search(stdQuery))
    }
    forward()
  }
}
