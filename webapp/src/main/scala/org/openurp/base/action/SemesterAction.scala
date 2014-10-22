package org.openurp.base.action

import org.beangle.commons.lang.Numbers
import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{Calendar, Semester, WeekDay}
import org.openurp.base.domain.{CalendarBean, SemesterBean}

class CalendarAction extends RestfulAction[Calendar]{

  override def saveAndRedirect(entity:Calendar):View = {
    val calendar = entity.asInstanceOf[CalendarBean]
    calendar.firstDay=WeekDay(Numbers.toInt(get("firstday","1"))).asInstanceOf[WeekDay.WeekDay]
    super.saveAndRedirect(entity)
  }
}

class SemesterAction extends RestfulAction[Semester]{
  
  override def saveAndRedirect(entity:Semester):View = {
    val semester = entity.asInstanceOf[SemesterBean]
    semester.firstDay=WeekDay(Numbers.toInt(get("firstday","1"))).asInstanceOf[WeekDay.WeekDay]
    super.saveAndRedirect(entity)
  }
}