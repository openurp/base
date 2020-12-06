/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.admin.edu.code

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.code.edu.model.{AcademicLevel, EducationLevel, ExamMode, ExamStatus, GradingMode}
import org.openurp.code.sin.model.BookCategory
import org.openurp.code.sin.model.Press
import org.openurp.code.sin.model.PressGrade


class GradingModeAction extends RestfulAction[GradingMode]

class ExamModeAction extends RestfulAction[ExamMode]

class ExamStatusAction extends RestfulAction[ExamStatus]

class EducationLevelAction extends RestfulAction[EducationLevel] {
  override protected def editSetting(entity: EducationLevel): Unit = {
    put("levels", entityDao.getAll(classOf[AcademicLevel]))
    super.editSetting(entity)
  }
}


class PressAction extends RestfulAction[Press] {
  override def editSetting(entity: Press) = {
    put("grades", entityDao.getAll(classOf[PressGrade]))
  }
}

class BookCategoryAction extends RestfulAction[BookCategory]

class PressGradeAction extends RestfulAction[PressGrade]
