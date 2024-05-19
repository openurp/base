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

package org.openurp.base.web.action.admin.hr

import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.{Direction, Major}
import org.openurp.base.hr.model.TutorMajor
import org.openurp.base.model.Project
import org.openurp.starter.web.support.ProjectSupport

class TutorMajorAction extends RestfulAction[TutorMajor], ProjectSupport {

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("majors", findInProject(classOf[Major]))
    super.indexSetting()
  }

  override protected def editSetting(entity: TutorMajor): Unit = {
    given project: Project = getProject

    put("majors", findInProject(classOf[Major]))
    put("directions", findInProject(classOf[Direction]))
    put("project", project)
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: TutorMajor): View = {
    val directionIds = getAll("direction.id", classOf[Long])
    val newDirections = entityDao.find(classOf[Direction], directionIds)
    entity.directions.clear()
    entity.directions.addAll(newDirections)
    super.saveAndRedirect(entity)
  }

}
