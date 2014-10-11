package org.openurp.base.ws

import org.openurp.base.Holiday
import org.openurp.base.CourseUnit
import org.openurp.base.TimeSetting
import org.beangle.webmvc.entity.action.RestfulService

class HolidayAction extends RestfulService[Holiday]

class TimeSettingAction extends RestfulService[TimeSetting]

class CourseUnitAction extends RestfulService[CourseUnit]