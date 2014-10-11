package org.openurp.base.ws

import org.beangle.webmvc.entity.action.AbstractEntityAction
import org.openurp.base.Holiday
import org.openurp.base.CourseUnit
import org.openurp.base.TimeSetting

class HolidayAction extends AbstractEntityAction[Holiday]

class TimeSettingAction extends AbstractEntityAction[TimeSetting]

class CourseUnitAction extends AbstractEntityAction[CourseUnit]