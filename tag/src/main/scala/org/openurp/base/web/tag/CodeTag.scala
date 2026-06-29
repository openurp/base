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

package org.openurp.base.web.tag

import org.beangle.bui.Select
import org.beangle.ems.app.Ems
import org.beangle.template.api.ComponentContext
import org.openurp.base.model.Project
import org.openurp.code.service.CodeService

class CodeTag(context: ComponentContext) extends Select(context) {

  var `type`: String = _
  var project: Project = _

  var cache: String = "true"
  var codeService: CodeService = _

  def cacheable: Boolean = {
    cache == "true"
  }

  override def evaluateParams(): Unit = {
    if (null == project) ProjectHelper.getProject foreach { p => project = p }
    if (null == this.href) this.href = Ems.api + s"/base/code/${project.id}/${`type`}.json"
    super.evaluateParams()
  }
}
