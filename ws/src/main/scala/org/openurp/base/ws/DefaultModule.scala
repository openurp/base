/*
 * Copyright (C) 2005, The OpenURP Software.
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

package org.openurp.base.ws

import org.beangle.cdi.bind.BindModule
import org.openurp.code.service.impl.CodeServiceImpl

class DefaultModule extends BindModule {

  protected override def binding(): Unit = {
    bind(classOf[edu.TeacherWS])
    bind(classOf[stu.InstructorWS])
    bind(classOf[edu.SemesterWS], classOf[edu.ClassroomWS])
    bind(classOf[UserWS], classOf[DepartmentWS])
    bind(classOf[BuildingWS],classOf[CampusWS])
    bind(classOf[CodeWS])

    bind(classOf[CodeServiceImpl])
  }
}
