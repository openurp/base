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
package org.openurp.base.web.action.edu.admin

import java.time.{Instant, LocalDate}

import org.beangle.commons.collection.Collections
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.openurp.base.edu.model.Classroom
import org.openurp.base.edu.web.helper.QueryHelper
import org.openurp.base.model.{Building, Campus, Department}
import org.openurp.code.edu.model.ClassroomType

class ClassroomAction extends ProjectRestfulAction[Classroom] {

  protected override def indexSetting(): Unit = {
    put("roomTypes", getCodes(classOf[ClassroomType]))
    put("campuses", findInSchool(classOf[Campus]))
  }

  override def getQueryBuilder: OqlBuilder[Classroom] = {
    val builder = super.getQueryBuilder
    QueryHelper.addTemporalOn(builder, getBoolean("active"))
    getBoolean("virtual") foreach { virtual =>
      builder.where(if (virtual) "classroom.roomNo is null" else "classroom.roomNo is not null")
    }
    builder
  }

  override protected def saveAndRedirect(entity: Classroom): View = {
    entity.updatedAt = Instant.now
    entity.beginOn = LocalDate.now()

    val departIds = getAll("departId2nd", classOf[Int])
    val newDeparts = entityDao.find(classOf[Department], departIds)
    val removed = entity.departs filter { x => !newDeparts.contains(x) }
    entity.departs.subtractAll(removed)
    newDeparts foreach { l =>
      if (!entity.departs.contains(l))
        entity.departs += l
    }
    val project = getProject
    entity.school = project.school
    entity.projects += project
    super.saveAndRedirect(entity)
  }

  override def editSetting(entity: Classroom) = {
    if (null == entity.school) {
      entity.school = getProject.school
    }
    put("roomTypes", getCodes(classOf[ClassroomType]))
    put("campuses", findInSchool(classOf[Campus]))
    put("buildings", findInSchool(classOf[Building]))
    val departs = Collections.newBuffer[Department]
    departs ++= getProject.departments
    departs --= entity.departs
    put("departs", departs)
  }

}
