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

package org.openurp.base.web.action.admin.edu

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.edu.model.{CourseUnit, TimeSetting}
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.code.edu.model.DayPart

class TimeSettingAction extends ProjectRestfulAction[TimeSetting] {

  override protected def editSetting(setting: TimeSetting): Unit = {
    put("campuses", getProject.campuses)
  }
}

class CourseUnitAction extends ProjectRestfulAction[CourseUnit] {

  override protected def indexSetting(): Unit = {
    put("settings", entityDao.findBy(classOf[TimeSetting], "project", List(getProject)))
  }

  override protected def getQueryBuilder: OqlBuilder[CourseUnit] = {
    val builder: OqlBuilder[CourseUnit] = OqlBuilder.from(entityName, "courseUnit")
    builder.where("courseUnit.setting.project=:project", getProject)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: CourseUnit): Unit = {
    put("settings", entityDao.findBy(classOf[TimeSetting], "project", List(getProject)))
    put("parts", entityDao.getAll(classOf[DayPart]))
  }

}
