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
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.helper.QueryHelper
import org.openurp.base.hr.model.President
import org.openurp.base.model.*
import org.openurp.base.web.action.admin.ProjectRestfulAction

import java.time.LocalDate

/** 校长
 */
class PresidentAction extends ProjectRestfulAction[President] {

  override def getQueryBuilder: OqlBuilder[President] = {
    val query = super.getQueryBuilder
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(president: President) = {
    given project: Project = getProject

    if (!president.persisted) {
      president.beginOn = LocalDate.now
    }
    super.editSetting(president)
  }

  override protected def saveAndRedirect(president: President): View = {
    given project: Project = getProject

    if (president.school == null) {
      president.school = project.school
    }
    super.saveAndRedirect(president)
  }

}
