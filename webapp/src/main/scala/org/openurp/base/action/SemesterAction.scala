package org.openurp.base.action

import org.beangle.commons.lang.Numbers
import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.Calendar
import org.openurp.base.Semester
import org.openurp.base.WeekDay
import org.openurp.base.domain.CalendarBean
import org.openurp.base.domain.SemesterBean

class CalendarAction extends RestfulAction[Calendar]{
  
  override def editSetting(entity: Calendar) = {
    val query = OqlBuilder.from(classOf[Semester])
    query.orderBy("name")
    val semesters = entityDao.search(query)
    put("semesters", semesters)
    super.editSetting(entity)
  }
  
  override def saveAndRedirect(entity:Calendar):View = {
    val calendar = entity.asInstanceOf[CalendarBean]
    calendar.firstDay=WeekDay(Numbers.toInt(get("firstday","1"))).asInstanceOf[WeekDay.WeekDay]
    super.saveAndRedirect(entity)
  }
}

class SemesterAction extends RestfulAction[Semester]{
  
  override def editSetting(entity: Semester) = {

    val query = OqlBuilder.from(classOf[Calendar])
    query.orderBy("name")
    val calendars = entityDao.search(query)
    put("calendars", calendars)
    super.editSetting(entity)
  }
  
  override def saveAndRedirect(entity:Semester):View = {
    val semester = entity.asInstanceOf[SemesterBean]
    semester.firstDay=WeekDay(Numbers.toInt(get("firstday","1"))).asInstanceOf[WeekDay.WeekDay]
    super.saveAndRedirect(entity)
  }
}