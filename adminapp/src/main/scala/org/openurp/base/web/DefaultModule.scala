/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2005, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web

import org.beangle.cdi.bind.BindModule
import org.openurp.base.web.action.BuildingAction
import org.openurp.base.web.action.CampusAction
import org.openurp.base.web.action.DepartmentAction
import org.openurp.base.web.action.RoomAction
import org.openurp.base.web.action.SchoolAction
import org.openurp.base.web.action.UserAction
import org.openurp.base.web.action.code.CountryAction
import org.openurp.base.web.action.code.DisciplineCategoryAction
import org.openurp.base.web.action.code.DivisionAction
import org.openurp.base.web.action.code.FamilyRelationshipAction
import org.openurp.base.web.action.code.GenderAction
import org.openurp.base.web.action.code.IdTypeAction
import org.openurp.base.web.action.code.InstitutionAction
import org.openurp.base.web.action.code.LanguageAction
import org.openurp.base.web.action.code.NationAction
import org.openurp.base.web.action.code.PoliticalStatusAction
import org.openurp.base.web.action.code.UserCategoryAction
import org.openurp.base.web.action.SemesterAction
import org.openurp.base.web.action.CalendarAction
import org.openurp.base.web.action.CourseUnitAction
import org.openurp.base.web.action.TimeSettingAction
import org.openurp.base.web.action.HolidayAction
import org.openurp.base.web.action.IndexAction

class DefaultModule extends BindModule {

  protected override def binding() {
    bind(classOf[GenderAction], classOf[CountryAction], classOf[NationAction],
      classOf[PoliticalStatusAction], classOf[LanguageAction],
      classOf[DivisionAction], classOf[FamilyRelationshipAction])
    bind(classOf[InstitutionAction], classOf[DisciplineCategoryAction])
    bind(classOf[IdTypeAction], classOf[UserCategoryAction])
    bind(classOf[DepartmentAction], classOf[SchoolAction])
    bind(classOf[UserAction])
    bind(classOf[SemesterAction], classOf[CalendarAction])
    bind(classOf[TimeSettingAction], classOf[CourseUnitAction], classOf[HolidayAction])
    bind(classOf[CampusAction], classOf[RoomAction], classOf[BuildingAction])
    bind(classOf[IndexAction])
  }
}
