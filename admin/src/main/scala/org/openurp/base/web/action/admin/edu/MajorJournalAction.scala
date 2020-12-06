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
package org.openurp.base.web.action.admin.edu

import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.code.edu.model.{DisciplineCategory, EducationLevel}
import org.openurp.base.edu.model.MajorJournal
import org.openurp.boot.edu.helper.ProjectSupport

class MajorJournalAction extends RestfulAction[MajorJournal] with ProjectSupport {

  override def editSetting(entity: MajorJournal) = {
    put("categories", getCodes(classOf[DisciplineCategory]))
    put("levels", getCodes(classOf[EducationLevel]))
    put("departs", findInSchool(classOf[Department]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: MajorJournal): View = {
    val view = super.saveAndRedirect(entity)
    entityDao.refresh(entity)
    entity.major.beginOn = entity.major.journals.map(_.beginOn).min
    entityDao.saveOrUpdate(entity.major)
    entityDao.evict(entity.major)
    view
  }

  override protected def removeAndRedirect(entities: Seq[MajorJournal]): View = {
    val majors = entities.map(_.major).toSet
    val view = super.removeAndRedirect(entities)
    majors foreach { m =>
      entityDao.evict(m)
    }
    view
  }
}
