package org.openurp.base.ws

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.base.ws.code.{ CountryAction, DivisionAction, EducationAction, FamilyRelationAction, GenderAction, LanguageAction, NationAction, PoliticalAffiliationAction }

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[GenderAction], classOf[NationAction])
    bind(classOf[CountryAction], classOf[PoliticalAffiliationAction])
    bind(classOf[LanguageAction], classOf[DivisionAction])
    bind(classOf[EducationAction], classOf[FamilyRelationAction])
  }

}