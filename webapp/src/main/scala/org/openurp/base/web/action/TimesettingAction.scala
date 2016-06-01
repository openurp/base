package org.openurp.base.web.action

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.webmvc.api.annotation.ignore
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.CourseUnit
import org.openurp.base.model.Holiday
import org.openurp.base.model.TimeSetting
import org.openurp.base.code.model.DayPart
import org.openurp.base.model.School

@action("{school}/holiday")
class HolidayAction extends RestfulAction[Holiday] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[Holiday] = {
    val builder: OqlBuilder[Holiday] = OqlBuilder.from(entityName, "holiday")
    builder.where("holiday.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  @ignore
  override protected def saveAndRedirect(entity: Holiday): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }

}

@action("{school}/time-setting")
class TimeSettingAction extends RestfulAction[TimeSetting] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[TimeSetting] = {
    val builder: OqlBuilder[TimeSetting] = OqlBuilder.from(entityName, "timeSetting")
    builder.where("timeSetting.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  @ignore
  override protected def saveAndRedirect(entity: TimeSetting): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
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

  override protected def editSetting(entity: CourseUnit): Unit = {
    val school = entityDao.findBy(classOf[School], "code", List(get("school").get))
    put("settings", entityDao.findBy(classOf[TimeSetting], "school", school))
    put("parts", entityDao.getAll(classOf[DayPart]))
  }

}