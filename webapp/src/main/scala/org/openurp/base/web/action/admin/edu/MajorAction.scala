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
import org.beangle.she.webmvc.{ExportSupport, QueryHelper}
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.{Major, MajorDiscipline}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.code.edu.model.{DisciplineCategory, EducationLevel}

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

  override protected def saveAndRedirect(major: Major): View = {
    val rs = super.saveAndRedirect(major)
    val category = entityDao.get(classOf[DisciplineCategory], getIntId("category"))
    if (major.disciplines.nonEmpty) {
      major.disciplines.maxBy(_.beginOn).category = category
    } else {
      val md = new MajorDiscipline
      md.major = major
      md.category = category
      md.beginOn = major.beginOn
      major.disciplines.addOne(md)
    }
    entityDao.saveOrUpdate(major)
    rs
  }

  override def editSetting(major: Major): Unit = {
    val project = getProject
    put("project", project)
    if !major.persisted then major.beginOn = LocalDate.now
    if (null == major.project) {
      major.project = project
    }
    if (major.disciplines.nonEmpty) {
      put("category", major.disciplines.maxBy(_.beginOn).category)
    }
    super.editSetting(major)
  }

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departs", getDeparts)
    put("levels", getCodes(classOf[EducationLevel]))
  }

}
