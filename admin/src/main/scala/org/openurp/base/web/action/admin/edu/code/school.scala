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

package org.openurp.base.web.action.admin.edu.code

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.code.*
import org.openurp.base.std.code.StdType
import org.openurp.base.web.action.admin.CodeRestfulAction
import org.openurp.code.edu.model.{CourseNature, TeachingMethod, TeachingNature, TeachingNatureCategory}
import org.openurp.code.std.model.{StdAlterReason, StdAlterType}

class CourseAbilityRateAction extends CodeRestfulAction[CourseAbilityRate]

class TeachingNatureAction extends CodeRestfulAction[TeachingNature] {
  override def editSetting(entity: TeachingNature) = {
    put("categories", TeachingNatureCategory.values)
    super.editSetting(entity)
  }
}

class CourseNatureAction extends CodeRestfulAction[CourseNature]

class TeachingMethodAction extends CodeRestfulAction[TeachingMethod]

class CourseTypeAction extends CodeRestfulAction[CourseType]

class CourseCategoryAction extends CodeRestfulAction[CourseCategory]

class BookTypeAction extends CodeRestfulAction[BookType]

class BookAwardTypeAction extends CodeRestfulAction[BookAwardType]

class EducationTypeAction extends CodeRestfulAction[EducationType]
