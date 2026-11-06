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

package org.openurp.base.ws.admin

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.edu.model.Holiday
import org.openurp.base.model.*

/** /api/base/admin/calendars */
class CalendarWS extends AdminJsonWS[Calendar] {

  override protected def getQueryBuilder: OqlBuilder[Calendar] = {
    val builder = OqlBuilder.from(classOf[Calendar], "calendar")
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }
}

/** /api/base/admin/calendar-stages */
class CalendarStageWS extends AdminJsonWS[CalendarStage]

/** /api/base/admin/semesters */
class SemesterWS extends ProjectAdminJsonWS[Semester] {

  override protected def getQueryBuilder: OqlBuilder[Semester] = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    populateConditions(builder)
    builder.where("semester.calendar.school=:school", getProject.school)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }
}

/** /api/base/admin/school-years */
class SchoolYearWS extends ProjectAdminJsonWS[SchoolYear] {

  override protected def getQueryBuilder: OqlBuilder[SchoolYear] = {
    val builder = OqlBuilder.from(classOf[SchoolYear], "schoolYear")
    populateConditions(builder)
    builder.where("schoolYear.calendar.school=:school", getProject.school)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def prepare(entity: SchoolYear): Unit = {
    entity.startYear = entity.name.substring(0, 4).toInt
  }
}

/** /api/base/admin/holidays */
class HolidayWS extends ProjectAdminJsonWS[Holiday]
