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

package org.openurp.base.web.action.info.hr

import org.beangle.data.dao.EntityDao
import org.beangle.webmvc.annotation.{mapping, param}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.view.View
import org.openurp.base.hr.model.StaffProfile

class ProfileAction extends ActionSupport {

  var entityDao: EntityDao = _

  @mapping("{id}")
  def index(@param("id") id: String): View = {
    val profile = entityDao.findBy(classOf[StaffProfile], "staff.id", id.toLong)
    put("profile", profile.headOption)
    forward()
  }
}
