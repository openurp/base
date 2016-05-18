package org.openurp.base.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.{ CourseUnit, Holiday, TimeSetting }
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order

@action("{school}/holiday")
class HolidayAction extends RestfulAction[Holiday] {
  override protected def getQueryBuilder(): OqlBuilder[Holiday] = {
    val builder: OqlBuilder[Holiday] = OqlBuilder.from(entityName, "holiday")
    builder.where("holiday.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}

@action("{school}/time-setting")
class TimeSettingAction extends RestfulAction[TimeSetting] {
  override protected def getQueryBuilder(): OqlBuilder[TimeSetting] = {
    val builder: OqlBuilder[TimeSetting] = OqlBuilder.from(entityName, "timeSetting")
    builder.where("timeSetting.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
@action("{school}/course-unit")
class CourseUnitAction extends RestfulAction[CourseUnit] {
  override protected def getQueryBuilder(): OqlBuilder[CourseUnit] = {
    val builder: OqlBuilder[CourseUnit] = OqlBuilder.from(entityName, "courseUnit")
    builder.where("courseUnit.setting.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}