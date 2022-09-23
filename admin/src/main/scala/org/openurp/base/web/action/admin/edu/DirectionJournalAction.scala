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

import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.{Direction, DirectionJournal}
import org.openurp.base.model.{Department, Project}
import org.openurp.code.edu.model.EducationLevel
import org.openurp.starter.web.support.ProjectSupport

class DirectionJournalAction extends RestfulAction[DirectionJournal] with ProjectSupport {
  override def editSetting(entity: DirectionJournal) = {
    given project: Project = getProject

    put("directions", entityDao.findBy(classOf[Direction], "project", project))
    put("levels", getCodes(classOf[EducationLevel]))
    put("departs", findInSchool(classOf[Department]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(dj: DirectionJournal): View = {
    val view = super.saveAndRedirect(dj)
    entityDao.evict(classOf[Direction])
    val direction = entityDao.get(classOf[Direction], dj.direction.id)
    direction.beginOn = direction.journals.map(_.beginOn).min
    entityDao.saveOrUpdate(direction)
    view
  }
}
