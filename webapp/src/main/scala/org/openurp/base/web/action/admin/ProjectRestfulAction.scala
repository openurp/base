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

import org.beangle.commons.bean.Properties
import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.view.View
import org.openurp.starter.web.support.ProjectSupport

abstract class ProjectRestfulAction[T <: Entity[_]] extends RestfulAction[T], ProjectSupport {

  var databus: DataEventBus = _

  override protected def getQueryBuilder: OqlBuilder[T] = {
    val builder = OqlBuilder.from(entityClass, simpleEntityName)
    populateConditions(builder)
    val entityType = entityDao.domain.getEntity(entityClass).get
    entityType.getProperty("project") foreach { d =>
      builder.where(simpleEntityName + ".project = :project", getProject)
    }
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    builder.tailOrder(simpleEntityName + ".id")
  }

  override protected def saveAndRedirect(entity: T): View = {
    Properties.set(entity, "project", getProject)
    val rs = super.saveAndRedirect(entity)
    if (entityDao.domain.getEntity(entityClass).get.cacheable) {
      databus.publish(DataEvent.update(entity))
    }
    rs
  }

}
