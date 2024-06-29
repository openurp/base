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

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.edu.model.{Direction, Major, MajorDirector}
import org.openurp.base.model.Project
import org.openurp.starter.web.support.ProjectSupport

/** 专业负责人维护
 */
class MajorDirectorAction extends RestfulAction[MajorDirector], ProjectSupport {

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departs", getDeparts)
    super.indexSetting()
  }

  override protected def editSetting(md: MajorDirector): Unit = {
    given project: Project = getProject

    put("project", project)
    put("majors", findInProject(classOf[Major]))
    put("directions", findInProject(classOf[Direction]))
    super.editSetting(md)
  }

  override protected def getQueryBuilder: OqlBuilder[MajorDirector] = {
    val query = super.getQueryBuilder
    queryByDepart(query, "director.director.department")
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override protected def simpleEntityName: String = {
    "director"
  }
}
