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

package org.openurp.base.web.action.admin.edu

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.action
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.{Calendar, CalendarStage, Semester}
import org.openurp.base.model.School

class CalendarAction extends RestfulAction[Calendar] {
  override protected def getQueryBuilder: OqlBuilder[Calendar] = {
    val builder: OqlBuilder[Calendar] = OqlBuilder.from(entityName, "calendar")
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: Calendar): Unit = {
    put("schools", entityDao.getAll(classOf[School]))
  }
}

class CalendarStageAction extends RestfulAction[CalendarStage]{
  override protected def editSetting(entity: CalendarStage): Unit = {
    put("schools", entityDao.getAll(classOf[School]))
  }
}

class SemesterAction extends RestfulAction[Semester] {
  override protected def getQueryBuilder: OqlBuilder[Semester] = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  protected override def editSetting(entity: Semester): Unit = {
    put("calendars", entityDao.getAll(classOf[Calendar]))
  }
}
