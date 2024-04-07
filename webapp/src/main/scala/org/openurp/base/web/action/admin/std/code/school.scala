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

package org.openurp.base.web.action.admin.std.code

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.code.std.model.{StdLabel, StdLabelType, StdType}
import org.openurp.base.web.action.admin.CodeRestfulAction

class StdLabelAction extends CodeRestfulAction[StdLabel] {
  override def editSetting(entity: StdLabel) = {
    val query = OqlBuilder.from(classOf[StdLabelType])
    query.orderBy("name")
    val labelTypes = entityDao.search(query)
    put("labelTypes", labelTypes)
    super.editSetting(entity)
  }
}

class StdLabelTypeAction extends CodeRestfulAction[StdLabelType]

class StdTypeAction extends CodeRestfulAction[StdType]
