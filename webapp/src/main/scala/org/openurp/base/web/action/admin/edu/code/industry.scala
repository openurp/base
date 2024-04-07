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
import org.openurp.base.web.action.admin.CodeRestfulAction
import org.openurp.code.edu.model.*
import org.openurp.code.sin.model.{BookCategory, Press, PressGrade}

class GradingModeAction extends CodeRestfulAction[GradingMode]

class ExamModeAction extends CodeRestfulAction[ExamMode]

class ExamStatusAction extends CodeRestfulAction[ExamStatus]

class EducationLevelAction extends CodeRestfulAction[EducationLevel] {
  override protected def editSetting(entity: EducationLevel): Unit = {
    put("levels", entityDao.getAll(classOf[AcademicLevel]))
    super.editSetting(entity)
  }
}

class PressAction extends CodeRestfulAction[Press] {
  override def editSetting(press: Press) = {
    put("grades", entityDao.getAll(classOf[PressGrade]))
    super.editSetting(press)
  }
}

class BookCategoryAction extends CodeRestfulAction[BookCategory]

class PressGradeAction extends CodeRestfulAction[PressGrade]

class ExamTypeAction extends CodeRestfulAction[ExamType]

class GradeTypeAction extends CodeRestfulAction[GradeType] {
  override def editSetting(entity: GradeType) = {
    put("examTypes", entityDao.getAll(classOf[ExamType]))
    super.editSetting(entity)
  }
}
class CourseTakeTypeAction extends CodeRestfulAction[CourseTakeType]
