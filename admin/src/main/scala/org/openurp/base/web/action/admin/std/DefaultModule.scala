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

package org.openurp.base.web.action.admin.std

import org.beangle.cdi.bind.BindModule
import org.openurp.base.std.service.impl.SquadServiceImpl

class DefaultModule extends BindModule {

  protected override def binding(): Unit = {
    bind(classOf[SquadServiceImpl])
    bind(classOf[SquadAction], classOf[MentorAction])
    bind(classOf[GradeAction], classOf[GraduateSeasonAction])

    bind(classOf[CodeAction])
    bind(classOf[code.StdLabelAction], classOf[code.StdLabelTypeAction])
    bind(classOf[code.StudentStatusAction], classOf[code.StdTypeAction])
    bind(classOf[code.StdAlterTypeAction], classOf[code.StdAlterReasonAction])
  }
}
