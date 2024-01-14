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

package org.openurp.base.web.action.admin.space

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.ignore
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.model.Campus
import org.openurp.base.space.model.{Building, Room}
import org.openurp.base.web.action.admin.SchoolSupport
import org.openurp.code.asset.model.RoomType

import java.time.LocalDate

class RoomAction extends RestfulAction[Room] with SchoolSupport {

  override protected def editSetting(room: Room): Unit = {
    put("roomTypes", entityDao.getAll(classOf[RoomType]))
    put("campuses", getCampuses)
    put("departments", getDepartments)
    put("buildings", getBuildings)
    if !room.persisted then room.beginOn = LocalDate.now
  }

  override protected def indexSetting(): Unit = {
    put("roomTypes", entityDao.getAll(classOf[RoomType]))
    put("campuses", getCampuses)
    put("buildings", getBuildings)
  }

  private def getCampuses: Seq[Campus] = {
    entityDao.search(OqlBuilder.from(classOf[Campus], "c").where("c.school=:school", getSchool))
  }

  private def getBuildings: Seq[Building] = {
    entityDao.search(OqlBuilder.from(classOf[Building], "c").where("c.school=:school", getSchool))
  }

  override protected def getQueryBuilder: OqlBuilder[Room] = {
    val builder = OqlBuilder.from(classOf[Room], "room")
    builder.where("room.school=:school", getSchool)
    populateConditions(builder)
    QueryHelper.addActive(builder, getBoolean("active"))
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  @ignore
  override protected def saveAndRedirect(entity: Room): View = {
    entity.school = getSchool
    entity.building foreach { building =>
      val b = entityDao.get(classOf[Building], building.id)
      entity.campus = b.campus
    }
    super.saveAndRedirect(entity)
  }

}
