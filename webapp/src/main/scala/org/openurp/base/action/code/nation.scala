package org.openurp.base.action.code

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.{ Country, Division, Education, FamilyRelation, Gender, Language, Nation, PoliticalAffiliation }

class GenderAction extends RestfulAction[Gender]

class CountryAction extends RestfulAction[Country]

class NationAction extends RestfulAction[Nation]

class PoliticalAffiliationAction extends RestfulAction[PoliticalAffiliation]

class LanguageAction extends RestfulAction[Language]

class DivisionAction extends RestfulAction[Division]

class EducationAction extends RestfulAction[Education]

class FamilyRelationAction extends RestfulAction[FamilyRelation] 