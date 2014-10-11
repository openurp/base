package org.openurp.base.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.Holiday
import org.openurp.base.domain.TimeSettingBean
import org.openurp.base.TimeSetting
import org.openurp.base.domain.CourseUnitBean
import org.openurp.base.CourseUnit
import org.beangle.data.jpa.dao.OqlBuilder

class HolidayAction extends RestfulAction[Holiday]

class TimeSettingAction extends RestfulAction[TimeSetting]{
    
  override def editSetting(entity: TimeSetting) = {
    val query = OqlBuilder.from(classOf[CourseUnit])
    query.orderBy("name")
    val units = entityDao.search(query)
    put("units", units)
    super.editSetting(entity)
  }
  
}

class CourseUnitAction extends RestfulAction[CourseUnit]{
    
  override def editSetting(entity: CourseUnit) = {
    val query = OqlBuilder.from(classOf[TimeSetting])
    query.orderBy("name")
    val settings = entityDao.search(query)
    put("settings", settings)
    super.editSetting(entity)
  }
}