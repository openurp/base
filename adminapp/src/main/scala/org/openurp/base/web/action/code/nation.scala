package org.openurp.base.web.action.code

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.code.person.model.PoliticalStatus
import org.openurp.code.geo.model.Country
import org.openurp.code.person.model.Nation
import org.openurp.code.geo.model.Division
import org.openurp.code.edu.model.Language
import org.openurp.code.person.model.FamilyRelationship
import org.openurp.code.person.model.Gender

class GenderAction extends RestfulAction[Gender]

class CountryAction extends RestfulAction[Country]

class NationAction extends RestfulAction[Nation]

class PoliticalStatusAction extends RestfulAction[PoliticalStatus]

class LanguageAction extends RestfulAction[Language]

class DivisionAction extends RestfulAction[Division]

class FamilyRelationshipAction extends RestfulAction[FamilyRelationship]