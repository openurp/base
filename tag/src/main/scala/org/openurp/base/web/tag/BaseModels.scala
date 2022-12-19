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

import jakarta.servlet.http.HttpServletRequest
import org.beangle.template.api.{AbstractModels, ComponentContext, Tag}

class BaseModels(context: ComponentContext) extends AbstractModels(context) {

  def semester: Tag = get(classOf[SemesterPicker])

  def semester_bar: Tag = get(classOf[SemesterBar])

  def code: Tag = get(classOf[CodeTag])

  def staff: Tag = get(classOf[StaffTag])

  def grade: Tag = get(classOf[GradeTag])

  def campus: Tag = get(classOf[CampusTag])
}
