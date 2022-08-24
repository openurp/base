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

import org.beangle.cdi.bind.BindModule
import org.openurp.base.web.action.admin.code.*

class DefaultModule extends BindModule {

  protected override def binding(): Unit = {
    bind(classOf[GenderAction], classOf[CountryAction], classOf[NationAction],
      classOf[PoliticalStatusAction], classOf[LanguageAction],
      classOf[DivisionAction], classOf[FamilyRelationshipAction], classOf[IdTypeAction])

    bind(classOf[CodeAction],classOf[StaffAction])

    bind(classOf[InstitutionAction], classOf[RoomTypeAction])
    bind(classOf[UserCategoryAction], classOf[EduCategoryAction])

    bind(classOf[DepartmentAction], classOf[SchoolAction])
    bind(classOf[UserAction])
    bind(classOf[CampusAction], classOf[RoomAction], classOf[BuildingAction])

    bind(classOf[SemesterAction], classOf[CalendarAction], classOf[CalendarStageAction])
    bind(classOf[ProjectAction], classOf[ProjectCodeAction],classOf[ProjectPropertyAction])
  }
}
