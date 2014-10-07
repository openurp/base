package org.openurp.base.ws

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.base.ws.code.{ CountryAction, DivisionAction, EducationAction, FamilyRelationAction, GenderAction, LanguageAction, NationAction, PoliticalAffiliationAction }
import org.openurp.base.ws.code.InstitutionAction
import org.openurp.base.ws.code.DisciplineCategoryAction
import org.openurp.base.ws.code.RoomTypeAction
import org.openurp.base.ws.code.IdTypeAction
import org.openurp.base.ws.code.PersonCategoryAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[GenderAction], classOf[NationAction])
    bind(classOf[CountryAction], classOf[PoliticalAffiliationAction])
    bind(classOf[LanguageAction], classOf[DivisionAction])
    bind(classOf[EducationAction], classOf[FamilyRelationAction])

    bind(classOf[InstitutionAction], classOf[DisciplineCategoryAction])

    bind(classOf[IdTypeAction], classOf[RoomTypeAction], classOf[PersonCategoryAction])
  }

}