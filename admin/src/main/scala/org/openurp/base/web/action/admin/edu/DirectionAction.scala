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
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.{Direction, DirectionJournal, Major}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.code.edu.model.EducationLevel

import java.time.LocalDate

class DirectionAction extends ProjectRestfulAction[Direction] {

  override def indexSetting() = {
    given project: Project = getProject

    put("departs", getDeparts)
    put("levels", getCodes(classOf[EducationLevel]))
  }

  override def getQueryBuilder: OqlBuilder[Direction] = {
    val query = super.getQueryBuilder
    getInt("department.id") foreach { d =>
      query.where("exists(from direction.journals as dj where dj.depart.id=:departId)", d)
    }
    getInt("level.id") foreach { d =>
      query.where("exists(from direction.journals as dj where dj.level.id=:levelId)", d)
    }
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(direction: Direction) = {
    given project: Project = getProject

    if !direction.persisted then direction.beginOn = LocalDate.now
    val majors = findInProject(classOf[Major])
    put("majors", majors)
    super.editSetting(direction)
  }

  protected override def saveAndRedirect(entity: Direction): View = {
    entity.project = getProject

    if (!entity.persisted) {
      val major = entityDao.get(classOf[Major], entity.major.id)
      val departs = major.journals.map(j => j.depart).toSet
      val levels = major.journals.map(j => j.level).toSet
      if (departs.size == 1 && levels.size == 1) {
        val dj = new DirectionJournal
        dj.direction = entity
        dj.depart = departs.head
        dj.level = levels.head
        dj.beginOn = LocalDate.now
        entity.journals += dj
      }
    }
    val view = super.saveAndRedirect(entity)
    entityDao.evict(classOf[Direction])
    view
  }

}
