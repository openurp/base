package org.openurp.base.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{ CourseUnit, Holiday, TimeSetting }

class HolidayAction extends RestfulAction[Holiday]

class TimeSettingAction extends RestfulAction[TimeSetting]

class CourseUnitAction extends RestfulAction[CourseUnit]