package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{CourseUnit, Department, Holiday, TimeSetting}
import org.openurp.base.domain.{CourseUnitBean, TimeSettingBean}

class HolidayAction extends RestfulAction[Holiday]

class TimeSettingAction extends RestfulAction[TimeSetting] 

class CourseUnitAction extends RestfulAction[CourseUnit]