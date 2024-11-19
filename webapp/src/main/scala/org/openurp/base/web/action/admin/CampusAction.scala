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
import org.beangle.webmvc.annotation.{action, ignore}
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.Campus
import org.openurp.base.resource.model.Room
import org.openurp.code.asset.model.RoomType

import java.time.LocalDate

class CampusAction extends RestfulAction[Campus] with SchoolSupport {
  override protected def getQueryBuilder: OqlBuilder[Campus] = {
    val builder = OqlBuilder.from(classOf[Campus], "campus")
    builder.where("campus.school=:school", getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  protected override def editSetting(campus: Campus): Unit = {
    if !campus.persisted then campus.beginOn = LocalDate.now
    super.editSetting(campus)
  }

  @ignore
  override protected def saveAndRedirect(entity: Campus): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }
}
