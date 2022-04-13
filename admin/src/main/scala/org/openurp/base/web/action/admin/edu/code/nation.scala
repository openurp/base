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

import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.code.edu.model.DegreeLevel
import org.openurp.code.edu.model.Degree
import org.openurp.code.edu.model.EducationResult
import org.openurp.code.edu.model.StudyType
import org.openurp.code.edu.model.DisciplineCategory
import org.beangle.data.dao.OqlBuilder

class DegreeLevelAction extends RestfulAction[DegreeLevel]

class DegreeAction extends RestfulAction[Degree] {
  override def editSetting(entity: Degree) = {
    val query = OqlBuilder.from(classOf[DegreeLevel])
    query.orderBy("name")
    val levels = entityDao.search(query)
    put("levels", levels)
    super.editSetting(entity)
  }
}

class StudyTypeAction extends RestfulAction[StudyType]

class DisciplineCategoryAction extends RestfulAction[DisciplineCategory]

class EducationResultAction extends RestfulAction[EducationResult]
