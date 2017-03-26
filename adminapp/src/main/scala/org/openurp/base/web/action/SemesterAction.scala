package org.openurp.base.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.{ Calendar, Semester }
import org.beangle.commons.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order

@action("{school}/calendar")
class CalendarAction extends RestfulAction[Calendar] {
  override protected def getQueryBuilder(): OqlBuilder[Calendar] = {
    val builder: OqlBuilder[Calendar] = OqlBuilder.from(entityName, "calendar")
    builder.where("calendar.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
@action("{school}/semester")
class SemesterAction extends RestfulAction[Semester] {
  override protected def getQueryBuilder(): OqlBuilder[Semester] = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.calendar.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  protected override def editSetting(entity: Semester): Unit = {
    val builder = OqlBuilder.from(classOf[Calendar], "calendar")
    builder.where("calendar.school.code=:schoolCode", get("school").get)
    put("calendars",entityDao.search(builder))
  }
}