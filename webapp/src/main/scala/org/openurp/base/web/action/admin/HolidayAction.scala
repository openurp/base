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

package org.openurp.base.web.action.admin

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.Holiday
import org.openurp.base.web.tag.ProjectHelper.getProject
import org.openurp.starter.web.support.ProjectSupport

import java.time.Instant

class HolidayAction extends RestfulAction[Holiday], ProjectSupport {

  override def getQueryBuilder: OqlBuilder[Holiday] = {
    val query = super.getQueryBuilder
    query.where("holiday.project=:project", getProject)
    query
  }

  override protected def saveAndRedirect(holiday: Holiday): View = {
    holiday.project = getProject
    holiday.updatedAt = Instant.now
    super.saveAndRedirect(holiday)
  }

}
