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

package org.openurp.base.web.action.admin

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.annotation.action
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.{Calendar, CalendarStage, School, Semester}
import org.openurp.starter.web.support.ProjectSupport

class CalendarAction extends RestfulAction[Calendar] {
  override protected def getQueryBuilder: OqlBuilder[Calendar] = {
    val builder = OqlBuilder.from(classOf[Calendar], "calendar")
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: Calendar): Unit = {
    put("schools", entityDao.getAll(classOf[School]))
  }
}

class CalendarStageAction extends RestfulAction[CalendarStage] {
  override protected def editSetting(entity: CalendarStage): Unit = {
    put("schools", entityDao.getAll(classOf[School]))
  }
}

class SemesterAction extends RestfulAction[Semester], ProjectSupport {
  override protected def getQueryBuilder: OqlBuilder[Semester] = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    populateConditions(builder)
    builder.where("semester.calendar.school=:school", getProject.school)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  protected override def editSetting(entity: Semester): Unit = {
    val calendars = entityDao.findBy(classOf[Calendar], "school", getProject.school)
    put("calendars", calendars)
  }
}
