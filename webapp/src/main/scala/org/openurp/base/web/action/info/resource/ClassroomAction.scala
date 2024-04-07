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

package org.openurp.base.web.action.info.resource

import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.mapping
import org.beangle.web.action.view.View
import org.openurp.base.resource.model.{Building, Classroom}
import org.openurp.base.web.action.info.AbstractInfoAction

import java.time.LocalDate

class ClassroomAction extends AbstractInfoAction[Classroom] {

  def index(): View = {
    val project = getProject
    val builder = OqlBuilder.from(classOf[Classroom].getName, "c")
    builder.where("c.school=:school", project.school)
    builder.groupBy("c.campus.code,c.campus.name,c.building.id,c.building.code,c.building.name")
    builder.select("c.campus.name,c.building.id,c.building.name,count(*)")
    builder.where("c.endOn is null or c.endOn>:now", LocalDate.now)
    builder.orderBy("c.campus.code,c.building.code")
    val buildings = entityDao.search(builder)
    put("buildings", buildings)
    forward()
  }

  @mapping("{id}")
  def building(id: String): View = {
    val builder = OqlBuilder.from(classOf[Classroom], "c")
    val buildingId = id.toInt
    if (buildingId > 0) {
      put("building", entityDao.get(classOf[Building], buildingId))
      builder.where("c.building.id=:building_id", buildingId)
    } else {
      builder.where("c.building is null")
    }
    val project = getProject
    builder.where("c.school=:school", project.school)
    builder.where(":project in elements(c.projects)", project)
    builder.orderBy("c.code")
    builder.where("c.endOn is null or c.endOn>:now", LocalDate.now)
    put("classrooms", entityDao.search(builder))
    forward()
  }

}
