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

package org.openurp.base.web.action.info

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.time.WeekDay
import org.beangle.webmvc.annotation.mapping
import org.beangle.webmvc.view.View
import org.openurp.base.model.Semester

import java.time.LocalDate
import scala.collection.mutable

class SemesterAction extends AbstractInfoAction[Semester] {

  def index(): View = {
    val calendar = getProject.calendar
    put("calendar", calendar)
    put("semesters", calendar.semesters.filter(!_.year.archived))
    forward()
  }

  @mapping("{id}")
  def info(id: String): View = {
    val semester = entityDao.get(classOf[Semester], id.toInt)
    put("semester", semester)
    put("weekdays", WeekDay.values)

    val dates = Collections.newBuffer[mutable.Buffer[LocalDate]]
    var start = semester.beginOn
    val finish = semester.endOn
    // back to calendar first day
    while (start.getDayOfWeek.getValue != semester.calendar.firstWeekday.id) {
      start = start.minusDays(1)
    }
    //build by weeks
    var i = 0
    var weekDates: mutable.Buffer[LocalDate] = null
    while (!start.isAfter(finish)) {
      if (i == 0) {
        weekDates = Collections.newBuffer[LocalDate]
        dates += weekDates
      }
      weekDates += start
      i += 1
      i %= 7
      start = start.plusDays(1)
    }
    put("dates", dates)
    forward()
  }

}
