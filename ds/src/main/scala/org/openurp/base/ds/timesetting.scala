package org.openurp.base.ds

import org.openurp.base.Holiday
import org.openurp.base.CourseUnit
import org.openurp.base.TimeSetting
import org.beangle.webmvc.entity.action.RestfulService

class HolidayWS extends RestfulService[Holiday]

class TimeSettingWS extends RestfulService[TimeSetting]

class CourseUnitWS extends RestfulService[CourseUnit]