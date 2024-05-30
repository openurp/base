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

package org.openurp.base.web.action.admin.edu

import org.beangle.cdi.bind.BindModule
import org.openurp.base.web.helper.UrpUserHelper

class DefaultModule extends BindModule {

  protected override def binding(): Unit = {
    bind(classOf[MajorAction], classOf[DirectionAction], classOf[DirectionJournalAction], classOf[MajorJournalAction], classOf[MajorDisciplineAction])

    bind(classOf[CourseAction])
    bind(classOf[TextbookAction], classOf[CourseTextbookAction])
    bind(classOf[SchoolLengthAction])
    bind(classOf[TeachingOfficeAction])

    bind(classOf[TimeSettingAction], classOf[CourseUnitAction])
    bind(classOf[MinorMajorAction])

    //code mapping
    bind(classOf[CodeAction], classOf[CodeIndexAction])
    bind(classOf[UrpUserHelper]).nowire("platformDataSource")

  }
}
