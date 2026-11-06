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

package org.openurp.base.ws.admin

import org.beangle.commons.cdi.BindModule
import org.openurp.base.ws.admin.edu.*
import org.openurp.base.ws.admin.hr.*
import org.openurp.base.ws.admin.person.*
import org.openurp.base.ws.admin.resource.*
import org.openurp.base.ws.admin.std.*

class DefaultModule extends BindModule {

  protected override def binding(): Unit = {
    bind(classOf[SchoolWS], classOf[CampusWS], classOf[DepartmentWS], classOf[DepartTransitionWS])
    bind(classOf[ProjectWS], classOf[ProjectCodeWS], classOf[ProjectPropertyWS], classOf[UserWS], classOf[UserGroupWS])
    bind(classOf[CalendarWS], classOf[CalendarStageWS], classOf[SemesterWS], classOf[SchoolYearWS], classOf[HolidayWS])

    bind(classOf[MajorWS], classOf[DirectionWS], classOf[MajorJournalWS], classOf[DirectionJournalWS], classOf[MajorDisciplineWS])
    bind(classOf[CourseWS], classOf[CourseJournalWS], classOf[CourseTextbookWS], classOf[CourseUnitWS])
    bind(classOf[TextbookWS], classOf[NewBookWS], classOf[TeachingOfficeWS], classOf[TimeSettingWS], classOf[SchoolLengthWS], classOf[MinorMajorWS])
    bind(classOf[EduCodeWS])

    bind(classOf[StaffWS], classOf[TeacherWS], classOf[TutorWS], classOf[MentorWS])
    bind(classOf[SecretaryWS], classOf[CadreAssignmentWS], classOf[PresidentWS], classOf[MajorDirectorWS])
    bind(classOf[TutorMajorWS], classOf[TutorJournalWS], classOf[ProfileWS], classOf[MyProfileWS], classOf[TutorStatWS])
    bind(classOf[HrCodeWS])

    bind(classOf[SquadWS], classOf[GradeWS], classOf[GraduateSeasonWS], classOf[StdCodeWS])
    bind(classOf[BuildingWS], classOf[ClassroomWS], classOf[LaboratoryWS], classOf[DeviceWS])
    bind(classOf[RoomWS], classOf[LabCenterWS], classOf[ResourceCodeWS])
    bind(classOf[PersonCodeWS])
  }
}
