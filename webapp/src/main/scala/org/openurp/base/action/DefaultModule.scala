package org.openurp.base.model.action

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.base.model.action.code.CategoryAction
import org.openurp.base.model.action.code.CountryAction
import org.openurp.base.model.action.code.DisciplineCategoryAction
import org.openurp.base.model.action.code.DivisionAction
import org.openurp.base.model.action.code.EducationAction
import org.openurp.base.model.action.code.FamilyRelationshipAction
import org.openurp.base.model.action.code.GenderAction
import org.openurp.base.model.action.code.IdTypeAction
import org.openurp.base.model.action.code.InstitutionAction
import org.openurp.base.model.action.code.LanguageAction
import org.openurp.base.model.action.code.MetaAction
import org.openurp.base.model.action.code.NationAction
import org.openurp.base.model.action.code.PoliticalStatusAction
import org.openurp.base.model.action.code.RoomTypeAction
import org.openurp.base.model.action.code.UserCategoryAction
import org.openurp.platform.web.tag.UrpTagLibrary

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[MetaAction], classOf[CategoryAction])
    bind(classOf[GenderAction], classOf[CountryAction], classOf[NationAction],
      classOf[PoliticalStatusAction], classOf[LanguageAction],
      classOf[DivisionAction], classOf[EducationAction], classOf[FamilyRelationshipAction])
    bind(classOf[InstitutionAction], classOf[DisciplineCategoryAction])
    bind(classOf[IdTypeAction], classOf[RoomTypeAction])
    bind("mvc.Taglibrary.urp", classOf[UrpTagLibrary])
    bind(classOf[DepartmentAction], classOf[SchoolAction], classOf[UserAction])
    bind(classOf[UserCategoryAction])
    bind(classOf[CalendarAction], classOf[SemesterAction])
    bind(classOf[HolidayAction], classOf[TimeSettingAction], classOf[CourseUnitAction])
    bind(classOf[CampusAction], classOf[RoomAction], classOf[BuildingAction])
  }
}