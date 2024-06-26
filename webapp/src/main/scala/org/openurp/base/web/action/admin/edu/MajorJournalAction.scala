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
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.{Major, MajorJournal}
import org.openurp.base.model.{Department, Project}
import org.openurp.base.std.model.Student
import org.openurp.code.edu.model.{DisciplineCategory, EducationLevel}
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class MajorJournalAction extends RestfulAction[MajorJournal] with ProjectSupport {

  var databus: DataEventBus = _

  override def search(): View = {
    val journals = entityDao.search(getQueryBuilder)
    put("majorJournals", journals)

    getLong("majorJournal.major.id") foreach { majorId =>
      val query = OqlBuilder.from[Array[Any]](classOf[Student].getName, "std")
      query.where("std.state.major.id=:majorId", majorId)
        .groupBy("std.level.id,std.state.department.id")
        .select("std.level.id,std.state.department.id,count(*)")
      val stats = entityDao.search(query)
      put("stdCountMap", stats.map(x => (s"${x(0)}_${x(1)}", x(2))).toMap)
    }
    forward()
  }

  override def editSetting(journal: MajorJournal) = {
    given project: Project = getProject

    put("categories", getCodes(classOf[DisciplineCategory]))
    put("levels", getCodes(classOf[EducationLevel]))
    put("departs", findInSchool(classOf[Department]))
    if !journal.persisted then journal.beginOn = LocalDate.now
    super.editSetting(journal)
  }

  override protected def saveAndRedirect(mj: MajorJournal): View = {
    val view = super.saveAndRedirect(mj)
    entityDao.refresh(mj)
    entityDao.refresh(mj.major)
    if (mj.major.journals.nonEmpty) {
      mj.major.beginOn = mj.major.journals.map(_.beginOn).min
    }
    entityDao.saveOrUpdate(mj.major)
    entityDao.evict(classOf[Major])
    databus.publish(DataEvent.update(mj))
    databus.publish(DataEvent.update(mj.major))
    view
  }

  override protected def removeAndRedirect(entities: Seq[MajorJournal]): View = {
    val view = super.removeAndRedirect(entities)
    entityDao.evict(classOf[Major])
    databus.publish(DataEvent.remove(entities))
    view
  }
}
