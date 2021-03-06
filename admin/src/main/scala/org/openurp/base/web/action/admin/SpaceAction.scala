/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.admin

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.{action, ignore}
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.{Building, Campus, Room}
import org.openurp.code.asset.model.RoomType

class CampusAction extends RestfulAction[Campus] with SchoolSupport{
  override protected def getQueryBuilder: OqlBuilder[Campus] = {
    val builder: OqlBuilder[Campus] = OqlBuilder.from(entityName, "campus")
    builder.where("campus.school=:school",  getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }
  @ignore
  override protected def saveAndRedirect(entity: Campus): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }
}

@action("building")
class BuildingAction extends RestfulAction[Building] with SchoolSupport {
  override protected def getQueryBuilder: OqlBuilder[Building] = {
    val builder: OqlBuilder[Building] = OqlBuilder.from(entityName, "building")
    builder.where("building.school=:school", getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: Building): Unit = {
    put("campuses", getCampuses)
  }

  override protected def indexSetting(): Unit = {
    put("campuses", getCampuses)
  }

  private def getCampuses: Seq[Campus] = {
    entityDao.search(OqlBuilder.from(classOf[Campus], "c").where("c.school=:school", getSchool))
  }

  @ignore
  override protected def saveAndRedirect(entity: Building): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }
}

class RoomAction extends RestfulAction[Room] with SchoolSupport {

  override protected def editSetting(entity: Room): Unit = {
    put("roomTypes", entityDao.getAll(classOf[RoomType]))
    put("campuses", getCampuses)
    put("departments", getDepartments)
    put("buildings", getBuildings)
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
    val builder: OqlBuilder[Room] = OqlBuilder.from(entityName, "room")
    builder.where("room.school=:school", getSchool)
    populateConditions(builder)
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
