package org.openurp.base.model.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.{ CourseUnit, Holiday, TimeSetting }

class HolidayAction extends RestfulAction[Holiday]

class TimeSettingAction extends RestfulAction[TimeSetting]

class CourseUnitAction extends RestfulAction[CourseUnit]