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

package org.openurp.base.web.action.admin.edu

import org.beangle.cdi.bind.BindModule
import org.openurp.base.web.action.admin.edu.code.*
import org.openurp.base.web.helper.UrpUserHelper

class DefaultModule extends BindModule {

  protected override def binding(): Unit = {
    bind(classOf[MajorAction], classOf[DirectionAction], classOf[DirectionJournalAction], classOf[MajorJournalAction], classOf[MajorDisciplineAction])

    bind(classOf[CourseAction], classOf[TeacherAction])
    bind(classOf[ClassroomAction])
    bind(classOf[TextbookAction])
    bind(classOf[SchoolLengthAction])
    bind(classOf[TeachingOfficeAction])

    bind(classOf[TimeSettingAction], classOf[CourseUnitAction])

    //code mapping
    bind(classOf[CodeAction])
    bind(classOf[StdLabelAction], classOf[StdLabelTypeAction], classOf[StdTypeAction])
    bind(classOf[CourseAbilityRateAction], classOf[CourseTypeAction])
    bind(classOf[TeachingNatureAction], classOf[GradingModeAction])
    bind(classOf[CourseCategoryAction])
    bind(classOf[ExamModeAction], classOf[ExamStatusAction])
    bind(classOf[EducationLevelAction])
    bind(classOf[StdAlterTypeAction], classOf[StdAlterReasonAction])

    bind(classOf[CourseNatureAction], classOf[TeachingMethodAction])
    bind(classOf[PressAction], classOf[BookTypeAction], classOf[BookAwardTypeAction])
    bind(classOf[BookCategoryAction], classOf[PressGradeAction])

    bind(classOf[DegreeLevelAction], classOf[DegreeAction], classOf[StudyTypeAction],
      classOf[DisciplineCategoryAction], classOf[EducationResultAction])

    bind(classOf[UrpUserHelper]).nowire("platformDataSource")
  }
}
