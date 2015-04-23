package org.openurp.base.action.code

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.code.geo.Country
import org.openurp.code.person.Nation
import org.openurp.code.geo.Division
import org.openurp.code.edu.Language
import org.openurp.code.edu.Education
import org.openurp.code.person.Gender
import org.openurp.code.person.PoliticalStatus
import org.openurp.code.person.FamilyRelationship

class GenderAction extends RestfulAction[Gender]

class CountryAction extends RestfulAction[Country]

class NationAction extends RestfulAction[Nation]

class PoliticalStatusAction extends RestfulAction[PoliticalStatus]

class LanguageAction extends RestfulAction[Language]

class DivisionAction extends RestfulAction[Division]

class EducationAction extends RestfulAction[Education]

class FamilyRelationshipAction extends RestfulAction[FamilyRelationship] 