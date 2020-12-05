/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.web.tag

import org.beangle.webmvc.view.tag.ClosingUIBean
import org.beangle.webmvc.view.tag.ComponentContext

class SemesterBar(context: ComponentContext) extends ClosingUIBean(context) {

  var name: String = "semester.id"

  var label: String = _

  var required: String = "true"

  var value: Object = _

  var formName: String = "semesterForm"

  var action: String = _

  var target: String = _

  override def evaluateParams(): Unit = {
    if (null == id) {
      generateIdIfEmpty()
    }
  }
}
