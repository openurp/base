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

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.{Major, MajorDiscipline}
import org.openurp.code.edu.model.DisciplineCategory

import java.time.LocalDate

/**
 * @author xinzhou
 */
class MajorDisciplineAction extends RestfulAction[MajorDiscipline] {

  var databus: DataEventBus = _

  override def editSetting(discipline: MajorDiscipline): Unit = {
    put("majors", findItems(classOf[Major]))
    put("categories", findItems(classOf[DisciplineCategory]))
    if !discipline.persisted then discipline.beginOn = LocalDate.now
  }

  private def findItems[T <: Entity[_]](clazz: Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz)
    query.orderBy("name")
    val items = entityDao.search(query)
    items
  }

  override protected def saveAndRedirect(md: MajorDiscipline): View = {
    val view = super.saveAndRedirect(md)
    entityDao.evict(classOf[Major])
    databus.publish(DataEvent.update(md))
    view
  }

  override protected def removeAndRedirect(entities: Seq[MajorDiscipline]): View = {
    val majors = entities.map(_.major).toSet
    val view = super.removeAndRedirect(entities)
    entityDao.evict(classOf[Major])
    databus.publish(DataEvent.remove(entities))
    view
  }
}
