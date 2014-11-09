package org.openurp.base.action

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.base.action.code.{ CategoryAction, CountryAction, DisciplineCategoryAction, DivisionAction, EducationAction, FamilyRelationAction, GenderAction, IdTypeAction, InstitutionAction, LanguageAction, MetaAction, NationAction, PersonCategoryAction, PoliticalAffiliationAction, RoomTypeAction }
import org.openurp.platform.web.tag.UrpTagLibrary

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[MetaAction], classOf[CategoryAction])
    bind(classOf[GenderAction], classOf[CountryAction], classOf[NationAction],
      classOf[PoliticalAffiliationAction], classOf[LanguageAction],
      classOf[DivisionAction], classOf[EducationAction], classOf[FamilyRelationAction])
    bind(classOf[InstitutionAction], classOf[DisciplineCategoryAction])
    bind(classOf[IdTypeAction], classOf[RoomTypeAction])
    bind("mvc.Taglibrary.urp", classOf[UrpTagLibrary])
    bind(classOf[DepartmentAction], classOf[SchoolAction], classOf[PersonAction])
    bind(classOf[PersonCategoryAction])
    bind(classOf[CalendarAction], classOf[SemesterAction])
    bind(classOf[HolidayAction], classOf[TimeSettingAction], classOf[CourseUnitAction])
    bind(classOf[CampusAction], classOf[RoomAction], classOf[BuildingAction])
  }
}