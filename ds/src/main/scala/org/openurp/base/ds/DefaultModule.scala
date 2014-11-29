package org.openurp.base.ds

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.base.ds.code.{ CountryWS, DisciplineCategoryWS, DivisionWS, EducationWS, FamilyRelationWS, GenderWS, IdTypeWS, InstitutionWS, LanguageWS, NationWS, PersonCategoryWS, PoliticalAffiliationWS, RoomTypeWS }
import org.openurp.base.ds.code.TeacherTitleLevelWS
import org.openurp.base.ds.code.DegreeWS
import org.openurp.base.ds.code.TeacherTitleWS
import org.openurp.base.ds.code.TutorTypeWS
import org.openurp.base.ds.code.TeacherUnitTypeWS
import org.openurp.base.ds.code.TeacherTypeWS
import org.openurp.base.ds.code.TeacherStateWS

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[GenderWS], classOf[NationWS])
    bind(classOf[CountryWS], classOf[PoliticalAffiliationWS])
    bind(classOf[LanguageWS], classOf[DivisionWS])
    bind(classOf[EducationWS], classOf[FamilyRelationWS])

    bind(classOf[InstitutionWS], classOf[DisciplineCategoryWS])

    bind(classOf[IdTypeWS], classOf[RoomTypeWS], classOf[PersonCategoryWS])

    bind(classOf[DepartmentWS], classOf[SchoolWS])
    bind(classOf[PersonCategoryWS])
    bind(classOf[CalendarWS], classOf[SemesterWS])
    bind(classOf[HolidayWS], classOf[TimeSettingWS], classOf[CourseUnitWS])
    bind(classOf[CampusWS], classOf[RoomWS], classOf[BuildingWS])
    bind(classOf[TeacherTitleWS], classOf[TeacherTitleLevelWS], classOf[DegreeWS])
    bind(classOf[TeacherStateWS], classOf[TeacherTypeWS], classOf[TeacherUnitTypeWS], classOf[TutorTypeWS])
  }

}