package org.openurp.base.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.Holiday
import org.openurp.base.domain.TimeSettingBean
import org.openurp.base.TimeSetting
import org.openurp.base.domain.CourseUnitBean
import org.openurp.base.CourseUnit
import org.beangle.data.jpa.dao.OqlBuilder

class HolidayAction extends RestfulAction[Holiday]

class TimeSettingAction extends RestfulAction[TimeSetting]

class CourseUnitAction extends RestfulAction[CourseUnit]