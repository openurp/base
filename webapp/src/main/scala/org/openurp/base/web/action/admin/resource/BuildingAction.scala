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

package org.openurp.base.web.action.admin.resource

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.annotation.ignore
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.model.Campus
import org.openurp.base.resource.model.Building
import org.openurp.base.web.action.admin.SchoolSupport
import org.openurp.code.asset.model.BuildingType

import java.time.LocalDate

class BuildingAction extends RestfulAction[Building] with SchoolSupport {
  override protected def getQueryBuilder: OqlBuilder[Building] = {
    val builder = OqlBuilder.from(classOf[Building], "building")
    builder.where("building.school=:school", getSchool)
    populateConditions(builder)
    QueryHelper.addActive(builder, getBoolean("active"))
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(building: Building): Unit = {
    put("campuses", getCampuses)
    put("buildingTypes", codeService.get(classOf[BuildingType]))
    if !building.persisted then building.beginOn = LocalDate.now
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
