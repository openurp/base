package org.openurp.base.ds.code

import org.beangle.data.jpa.dao.OqlBuilder
import org.openurp.base.code.{ Country, Degree, Division, Education, FamilyRelation, Gender, Language, Nation, PoliticalAffiliation, TeacherTitle, TeacherTitleLevel }

class GenderWS extends AbstractWS[Gender]

class NationWS extends AbstractWS[Nation]

class CountryWS extends AbstractWS[Country] {
  override def buildQuery(builder: OqlBuilder[Country]): Unit = {
    builder.select("new org.openurp.base.ds.model.Country(code.id,code.code,code.name,code.alpha2Code)")
  }
}

class PoliticalAffiliationWS extends AbstractWS[PoliticalAffiliation]

class LanguageWS extends AbstractWS[Language]

class DivisionWS extends AbstractWS[Division] {

  override def buildQuery(builder: OqlBuilder[Division]): Unit = {
    get("parent") foreach { p =>
      builder.where("code.code like :code and code.code !=:mycode", p + "%", p)
    }
  }
}

class EducationWS extends AbstractWS[Education]

class FamilyRelationWS extends AbstractWS[FamilyRelation]

class TeacherTitleWS extends AbstractWS[TeacherTitle]

class TeacherTitleLevelWS extends AbstractWS[TeacherTitleLevel]

class DegreeWS extends AbstractWS[Degree]