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
import org.beangle.webmvc.support.action.ExportSupport
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.{Major, MajorDiscipline}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.code.edu.model.EducationLevel

import java.time.LocalDate

class MajorAction extends ProjectRestfulAction[Major], ExportSupport[Major] {

  override def getQueryBuilder: OqlBuilder[Major] = {
    val query = super.getQueryBuilder
    getInt("department.id") foreach { d =>
      query.where("exists(from major.journals as mj where mj.depart.id=:departId)", d)
    }
    getInt("level.id") foreach { d =>
      query.where("exists(from major.journals as mj where mj.level.id=:levelId)", d)
    }
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def saveAndRedirect(entity: Major): View = {
    val view = super.saveAndRedirect(entity)
    entityDao.evict(classOf[Major])
    view
  }

  override def editSetting(major: Major) = {
    put("projects", List(getProject))
    if !major.persisted then major.beginOn = LocalDate.now
    val disciplines = entityDao.getAll(classOf[MajorDiscipline])
    put("disciplines", disciplines)
    if (null == major.project) {
      major.project = getProject
    }
    super.editSetting(major)
  }

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departs", getDeparts)
    put("levels", getCodes(classOf[EducationLevel]))
  }

}
