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

package org.openurp.base.ws.admin.resource

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.she.webmvc.QueryHelper
import org.beangle.webmvc.annotation.action
import org.openurp.base.resource.model.{Building, LabCenter, Room}
import org.openurp.base.ws.admin.{AdminCodeWS, ProjectAdminJsonWS, SchoolAdminJsonWS}

import java.time.LocalDate

@action("code/{category}")
class ResourceCodeWS extends AdminCodeWS {
  override protected def codePackages: Set[String] = Set("org.openurp.code.asset.model")
}

/** /api/base/admin/resource/rooms */
class RoomWS extends SchoolAdminJsonWS[Room] {

  override protected def getQueryBuilder: OqlBuilder[Room] = {
    val builder = schoolScoped("room")
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  override protected def prepare(room: Room): Unit = {
    room.school = getSchool
    if (!room.persisted && null == room.beginOn) room.beginOn = LocalDate.now
    room.building foreach { building =>
      val loaded = entityDao.get(classOf[Building], building.id)
      room.campus = loaded.campus
    }
  }
}

/** /api/base/admin/resource/lab-centers */
class LabCenterWS extends ProjectAdminJsonWS[LabCenter] {

  override def getQueryBuilder: OqlBuilder[LabCenter] = {
    val builder = super.getQueryBuilder
    builder.where("labCenter.school=:school", getProject.school)
    builder
  }

  override protected def prepare(center: LabCenter): Unit = {
    if (null == center.beginOn) center.beginOn = LocalDate.now
    if (formSubmitted("labCenter.name") || formSubmitted("depart.id")) {
      val departIds = getAll("depart.id", classOf[Int])
      val newDeparts = entityDao.find(classOf[org.openurp.base.model.Department], departIds)
      center.departs.clear()
      center.departs.addAll(newDeparts)
    }
    center.school = getProject.school
  }
}
