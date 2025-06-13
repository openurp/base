/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.base.web.action.admin.edu

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.pojo.TemporalOn
import org.beangle.event.bus.DataEvent
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.{Course, CourseJournal, CourseJournalHour}
import org.openurp.base.model.{Project, Semester}
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.code.edu.model.{CourseTag, ExamMode, TeachingNature}

import java.time.LocalDate
import scala.collection.SortedMap

class CourseJournalAction extends ProjectRestfulAction[CourseJournal] {

  override def simpleEntityName: String = "journal"

  override protected def editSetting(journal: CourseJournal): Unit = {
    given project: Project = getProject

    put("tags", codeService.get(classOf[CourseTag]))
    put("teachingNatures", getCodes(classOf[TeachingNature]))
    put("examModes", getCodes(classOf[ExamMode]))
    put("departments", getDeparts)

    val query = OqlBuilder.from(classOf[Semester], "s")
    query.where("s.calendar=:calendar", project.calendar)
    query.orderBy("s.code desc")
    val semesters = entityDao.search(query)
    //学期起始年月的第一天，防止学期的起止日期变动导致对课程有效期的影响
    put("semesterDates", SortedMap.from(semesters.map(x => (atStartOfDay(x.beginOn).toString, atStartOfDay(x.beginOn).toString)).sortBy(_._1).reverse))
    if (!journal.persisted) {
      val course = entityDao.get(classOf[Course], journal.course.id)
      val beginOn = semesters.find(_.within(LocalDate.now)).map(x => atStartOfDay(x.beginOn)).getOrElse(LocalDate.now)
      put(simpleEntityName, new CourseJournal(course, beginOn))
    } else {
      put(simpleEntityName, journal)
    }
  }

  /** 日期对应当月的第一天的日期
   *
   * @param date
   * @return
   */
  private def atStartOfDay(date: LocalDate): LocalDate = {
    LocalDate.of(date.getYear, date.getMonth, 1)
  }

  override def getQueryBuilder: OqlBuilder[CourseJournal] = {
    given project: Project = getProject

    put("teachingNatures", getCodes(classOf[TeachingNature]))
    val query = super.getQueryBuilder
    queryByDepart(query, "journal.department")
    query
  }

  override protected def saveAndRedirect(journal: CourseJournal): View = {
    given project: Project = getProject

    val teachingNatures = getCodes(classOf[TeachingNature])
    teachingNatures foreach { ht =>
      val creditHour = getInt("creditHour" + ht.id)
      journal.hours find (h => h.nature == ht) match {
        case Some(hour) =>
          if (creditHour.isEmpty) {
            journal.hours -= hour
          } else {
            hour.creditHours = creditHour.getOrElse(0)
          }
        case None =>
          if (creditHour.nonEmpty) {
            val newHour = new CourseJournalHour(journal, ht, creditHour.getOrElse(0))
            journal.hours += newHour
          }
      }
    }
    val orphan = journal.hours.filter(x => !teachingNatures.contains(x.nature))
    journal.hours --= orphan
    journal.tags.clear()
    journal.tags.addAll(entityDao.find(classOf[CourseTag], getIntIds("tag")))
    entityDao.saveOrUpdate(journal)

    //计算journals的结束日期
    val journals = TemporalOn.calcEndOn(entityDao.findBy(classOf[CourseJournal], "course", journal.course))
    entityDao.saveOrUpdate(journals)
    val last = journals.last
    val course = entityDao.get(classOf[Course], last.course.id)
    //last one
    if (last.endOn.isEmpty) {
      if (last.enName.nonEmpty) {
        course.enName = last.enName
      }
      course.updateHours(last.hours.map(x => (x.nature, x.creditHours)).toMap)
      course.creditHours = last.creditHours
      course.name = last.name
    }
    if (journals.nonEmpty) {
      course.beginOn = journals.map(_.beginOn).min
      if (journals.exists(_.endOn.isEmpty)) {
        course.endOn = None
      } else {
        course.endOn = Some(journals.map(_.endOn.get).max)
      }
    }
    entityDao.saveOrUpdate(course)
    databus.publish(DataEvent.update(course))
    databus.publish(DataEvent.update(journal))
    super.saveAndRedirect(journal)
  }

}
