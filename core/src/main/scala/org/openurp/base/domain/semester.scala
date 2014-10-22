package org.openurp.base.domain

import org.beangle.data.model.bean.CodedBean
import org.beangle.data.model.bean.IntIdBean
import org.beangle.data.model.bean.NamedBean
import org.beangle.data.model.bean.NumIdBean
import org.beangle.data.model.bean.TemporalOnBean
import org.openurp.base.Calendar
import org.openurp.base.Semester
import org.openurp.base.WeekDay.WeekDay
import scala.collection.mutable.Buffer

/**
 * 教学日历方案
 * 校历（日历方案）记录了一整套学年学期的设置，是连贯性学年学期设置的集合，也可称日历方案。
 */
class CalendarBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean with Calendar {
  var semesters: Buffer[Semester] = new collection.mutable.ListBuffer[Semester]
  var firstDay: WeekDay = _
}

/**
 * 学年学期 </p> 代表的是具体学年度的 学期设置，每个学期的起始日期（起始日期要以星期日作为第一天）和结束日期。
 */
class SemesterBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean with Semester {
  /**日历*/
  var calendar: Calendar = _
  /**学年度,一般为yyyy-yyyy或者yyyy的格式*/
  var schoolYear: String = _

  var firstDay: WeekDay = _
}
