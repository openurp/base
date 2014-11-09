package org.openurp.base.ws.code

import org.openurp.base.code.{ IdType, PersonCategory, RoomType }
import org.openurp.base.code.TeacherUnitType
import org.openurp.base.code.TeacherType
import org.openurp.base.code.TutorType
import org.openurp.base.code.TeacherState

class IdTypeAction extends AbstractAction[IdType]

class RoomTypeAction extends AbstractAction[RoomType]

class PersonCategoryAction extends AbstractAction[PersonCategory]

class TeacherStateAction extends AbstractAction[TeacherState]

class TeacherTypeAction extends AbstractAction[TeacherType]

class TeacherUnitTypeAction extends AbstractAction[TeacherUnitType]

class TutorTypeAction extends AbstractAction[TutorType]