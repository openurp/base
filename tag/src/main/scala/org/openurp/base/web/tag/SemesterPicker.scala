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
import org.beangle.webmvc.context.ActionContext
import org.beangle.template.api.{ClosingUIBean,ComponentContext}
import org.openurp.base.model.{Project, Semester}

class SemesterPicker(context: ComponentContext) extends ClosingUIBean(context) {

  var name: String = "semester.id"

  var label: String = _

  var required: String = "true"

  var project: Project = _

  var value: Object = _

  var url: String = _

  var onchange: String = _

  override def evaluateParams(): Unit = {
    if (null == id) {
      generateIdIfEmpty()
    }
    value match {
      case s: Semester => value = s.id.toString
      case _ =>
    }
    if (null == project) {
      val p: Any = ActionContext.current.attribute("project")
      p match {
        case pjt: Project => project = pjt
        case _ =>
      }
      if (null == project) {
        throw new RuntimeException("Cannot find project in tag's parameters and request's attributes.")
      }
    }
    if (url == null) {
      url = Ems.api + s"/base/semesters/${project.id}.json"
    }

  }
}
