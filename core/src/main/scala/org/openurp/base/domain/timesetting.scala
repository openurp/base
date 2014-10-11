package org.openurp.base.domain

import java.{ util => ju }

import org.beangle.commons.lang.time.HourMinute
import org.beangle.data.model.bean.{ IntIdBean, NamedBean, NumIdBean, TemporalOnBean }
import org.openurp.base.{ CourseUnit, Holiday, TimeSetting }

/**
 * 假日安排
 */
class HolidayBean extends IntIdBean with NamedBean with TemporalOnBean with Holiday {
}

/**
 * 时间设置
 */
class TimeSettingBean extends IntIdBean with NamedBean with TimeSetting {
  var units: Seq[CourseUnit] = new collection.mutable.ListBuffer[CourseUnit]
}

class CourseUnitBean extends NumIdBean[java.lang.Short] with NamedBean with CourseUnit {
  var indexno: Int = _
  var startTime: HourMinute = _
  var endTime: HourMinute = _
  var setting: TimeSetting = _
}
