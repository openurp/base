/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2005, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.{ Calendar, Semester }
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order

@action("{school}/calendar")
class CalendarAction extends RestfulAction[Calendar] {
  override protected def getQueryBuilder(): OqlBuilder[Calendar] = {
    val builder: OqlBuilder[Calendar] = OqlBuilder.from(entityName, "calendar")
    builder.where("calendar.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
@action("{school}/semester")
class SemesterAction extends RestfulAction[Semester] {
  override protected def getQueryBuilder(): OqlBuilder[Semester] = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.calendar.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  protected override def editSetting(entity: Semester): Unit = {
    val builder = OqlBuilder.from(classOf[Calendar], "calendar")
    builder.where("calendar.school.code=:schoolCode", get("school").get)
    put("calendars",entityDao.search(builder))
  }
}
