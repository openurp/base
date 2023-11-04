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

import org.beangle.ems.app.Ems
import org.beangle.template.api.ComponentContext
import org.beangle.web.action.context.ActionContext
import org.beangle.webmvc.view.tag.Select
import org.openurp.base.model.Project

class CourseTag(context: ComponentContext) extends Select(context) {

  var project: Project = _

  override def evaluateParams(): Unit = {
    if (null == project) ProjectHelper.getProject foreach { p => project = p }
    if (null == this.href) this.href = Ems.api + s"/base/edu/${project.id}/courses.json?q={term}"
    if (null == this.option) this.option = "id,description"
    super.evaluateParams()
  }
}
