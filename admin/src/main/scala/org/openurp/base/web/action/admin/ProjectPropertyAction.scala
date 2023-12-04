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

import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.ProjectProperty
import org.openurp.base.service.Feature

class ProjectPropertyAction extends RestfulAction[ProjectProperty] {

  protected override def saveAndRedirect(property: ProjectProperty): View = {
    //must convert and validate
    try {
      val converted = Feature.convert(property.value, property.typeName)
    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"不能把${property.value}转换成${property.typeName}类型")
    }
    super.saveAndRedirect(property)
  }
}
