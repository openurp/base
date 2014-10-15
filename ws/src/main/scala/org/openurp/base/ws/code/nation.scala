package org.openurp.base.ws.code

import org.beangle.data.jpa.dao.OqlBuilder
import org.openurp.base.code.{ Country, Division, Education, FamilyRelation, Gender, Language, Nation, PoliticalAffiliation }

class GenderAction extends AbstractAction[Gender]

class NationAction extends AbstractAction[Nation]

class CountryAction extends AbstractAction[Country] {
  override def buildQuery(builder: OqlBuilder[Country]): Unit = {
    builder.select("new org.openurp.base.ws.model.Country(code.id,code.code,code.name,code.alpha2Code)")
  }
}

class PoliticalAffiliationAction extends AbstractAction[PoliticalAffiliation]

class LanguageAction extends AbstractAction[Language]

class DivisionAction extends AbstractAction[Division] {

  override def buildQuery(builder: OqlBuilder[Division]): Unit = {
    get("parent") foreach { p =>
      builder.where("code.code like :code and code.code !=:mycode", p + "%",p)
    }
  }
}

class EducationAction extends AbstractAction[Education]

class FamilyRelationAction extends AbstractAction[FamilyRelation]

