package org.openurp.base.ws

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.base.ws.code.{CountryAction, DisciplineCategoryAction, DivisionAction, EducationAction, FamilyRelationAction, GenderAction, IdTypeAction, InstitutionAction, LanguageAction, NationAction, PersonCategoryAction, PoliticalAffiliationAction, RoomTypeAction}

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[GenderAction], classOf[NationAction])
    bind(classOf[CountryAction], classOf[PoliticalAffiliationAction])
    bind(classOf[LanguageAction], classOf[DivisionAction])
    bind(classOf[EducationAction], classOf[FamilyRelationAction])

    bind(classOf[InstitutionAction], classOf[DisciplineCategoryAction])

    bind(classOf[IdTypeAction], classOf[RoomTypeAction], classOf[PersonCategoryAction])
    
    bind(classOf[DepartmentAction], classOf[SchoolAction])
    bind(classOf[PersonCategoryAction])
    bind(classOf[CalendarAction],classOf[SemesterAction])
    bind(classOf[HolidayAction],classOf[TimeSettingAction],classOf[CourseUnitAction])
    bind(classOf[CampusAction],classOf[RoomAction],classOf[BuildingAction])
  }

}