/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.base.web.action.admin.code

import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.web.action.admin.CodeRestfulAction
import org.openurp.code.edu.model.Language
import org.openurp.code.geo.model.{Country, Division}
import org.openurp.code.hr.model.{StaffType, WorkStatus}
import org.openurp.code.person.model.*

class GenderAction extends CodeRestfulAction[Gender]

class CountryAction extends CodeRestfulAction[Country]

class NationAction extends CodeRestfulAction[Nation]

class PoliticalStatusAction extends CodeRestfulAction[PoliticalStatus]

class LanguageAction extends CodeRestfulAction[Language]

class DivisionAction extends CodeRestfulAction[Division]

class FamilyRelationshipAction extends CodeRestfulAction[FamilyRelationship]

class IdTypeAction extends CodeRestfulAction[IdType]
