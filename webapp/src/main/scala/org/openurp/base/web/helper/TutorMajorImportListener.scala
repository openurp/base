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

package org.openurp.base.web.helper

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.doc.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.edu.model.{Direction, Major}
import org.openurp.base.hr.model.TutorMajor
import org.openurp.base.model.{Department, Project}
import org.openurp.code.edu.model.EducationLevel

class TutorMajorImportListener(entityDao: EntityDao, project: Project) extends ImportListener {

  def findExactly(major: Major, level: EducationLevel, department: Department, name: String): Option[Direction] = {
    val q = OqlBuilder.from(classOf[Direction], "d")
    q.where("d.major=:major", major)
    q.where("d.name = :name", name)
    q.where("d.endOn is null")
    val directions = entityDao.search(q).filter(_.journals.exists(x => x.level == level && x.endOn.isEmpty))
    if (directions.size == 1) directions.headOption
    else None
  }

  def findAppromax(major: Major, level: EducationLevel, department: Department, name: String): Option[Direction] = {
    val q = OqlBuilder.from(classOf[Direction], "d")
    q.where("d.major=:major", major)
    q.where("d.name like :name", "%" + name + "%")
    q.where("d.endOn is null")
    var directions = entityDao.search(q) filter (_.journals.exists(x => x.level == level && x.endOn.isEmpty))
    if (directions.size == 1) directions.headOption
    else
      directions = directions.filter(_.journals.exists(x => x.depart == department))
      if (directions.size == 1) directions.headOption
      else
        val names = Strings.split(Strings.replace(name, "——", "-"), "-")
        if (names.length > 1) {
          findAppromax2(major, level, department, names)
        } else None
  }

  def findAppromax2(major: Major, level: EducationLevel, department: Department, keywords: Array[String]): Option[Direction] = {
    val q = OqlBuilder.from(classOf[Direction], "d")
    q.where("d.major=:major", major)
    var i = 0
    val keyworkCon = Collections.newBuffer[String]
    while (i < keywords.length) {
      keyworkCon.addOne(s"d.name like :name${i}")
      i += 1
    }
    q.where(keyworkCon.mkString(" and "), keywords.map(x => "%" + x + "%"): _*)
    q.where("d.endOn is null")
    var directions = entityDao.search(q) filter (_.journals.exists(x => x.level == level && x.endOn.isEmpty))
    if (directions.size == 1) directions.headOption
    else
      directions = directions.filter(_.journals.exists(x => x.depart == department))
      if (directions.size == 1) directions.headOption
      else
        None
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val tm = transfer.current.asInstanceOf[TutorMajor]
    if (null != tm.staff && null != tm.major && null != tm.level && null != tm.eduType) {
      transfer.curData.get("directionNames") foreach { directionNames =>
        val directions = Collections.newBuffer[Direction]
        Strings.split(directionNames.toString, Array(',', '，', ';', '；', '、')) foreach { dn =>
          val direction = findExactly(tm.major, tm.level, tm.staff.department, dn).orElse(findAppromax(tm.major, tm.level, tm.staff.department, dn))
          if (direction.isEmpty) {
            tr.addFailure(s"${tm.major.name} ${tm.level.name} ${tm.staff.department.name} 下找不到唯一的方向", dn)
          } else {
            directions.addAll(direction)
          }
        }

        val q = OqlBuilder.from(classOf[TutorMajor], "m")
        q.where("m.staff=:staff and m.eduType=:eduType and m.level=:level and m.major=:major", tm.staff, tm.eduType, tm.level, tm.major)
        val exists = entityDao.search(q)
        if (exists.nonEmpty) {
          exists.head.directions.addAll(directions)
          entityDao.saveOrUpdate(exists)
        } else {
          tm.directions.addAll(directions)
          entityDao.saveOrUpdate(tm)
        }
      }
    }
  }

}
