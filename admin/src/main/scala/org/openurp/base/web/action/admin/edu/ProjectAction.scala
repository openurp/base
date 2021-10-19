/*
 * Copyright (C) 2005, The OpenURP Software.
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
import org.beangle.data.model.Entity
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.{Campus, Department, School}
import org.openurp.code.edu.model.{EduCategory, EducationLevel}
import org.openurp.base.edu.code.model.{StdLabel, StdType}
import org.openurp.base.edu.model.{Calendar, Project}

import scala.collection.mutable.Buffer

class ProjectAction extends RestfulAction[Project] {

  override def editSetting(entity: Project): Unit = {

    val school = entity.school

    val schools = findItems(classOf[School])
    put("schools", schools)

    val calendars = findInSchool(classOf[Calendar], school)
    put("calendars", calendars.subtractAll(entity.calendars))

    val campuses = findInSchool(classOf[Campus], school)
    put("campuses", campuses.subtractAll(entity.campuses))

    val departments = findInSchool(classOf[Department], school)
    put("departments", departments.subtractAll(entity.departments))

    val levels = findItems(classOf[EducationLevel])
    put("levels", levels.subtractAll(entity.levels))

    val labels = findItems(classOf[StdLabel])
    put("labels", labels.subtractAll(entity.stdLabels))

    val types = findItems(classOf[StdType])
    put("types", types.subtractAll(entity.stdTypes))

    put("eduCategories", this.entityDao.getAll(classOf[EduCategory]))

    super.editSetting(entity)
  }

  private def findItems[T <: Entity[_]](clazz: Class[T]): Buffer[T] = {
    val query = OqlBuilder.from(clazz)
    entityDao.search(query).toBuffer
  }

  private def findInSchool[T <: Entity[_]](clazz: Class[T], school: School): Buffer[T] = {
    val query = OqlBuilder.from(clazz, "aa")
    query.where("aa.school=:school", school)
    entityDao.search(query).toBuffer
  }

  private def findInProject[T <: Entity[_]](clazz: Class[T], project: Project): Buffer[T] = {
    val query = OqlBuilder.from(clazz, "aa")
    query.where("aa.school=:project", project)
    entityDao.search(query).toBuffer
  }

  protected override def saveAndRedirect(entity: Project): View = {

    entity.campuses.clear()
    val campusIds = getAll("campusesId2nd", classOf[Int])
    entity.campuses ++= entityDao.find(classOf[Campus], campusIds)

    entity.departments.clear()
    val departmentIds = getAll("departmentsId2nd", classOf[Int])
    entity.departments ++= entityDao.find(classOf[Department], departmentIds)

    entity.levels.clear()
    val levelIds = getAll("levelId2nd", classOf[Int])
    entity.levels ++= entityDao.find(classOf[EducationLevel], levelIds)

    entity.stdLabels.clear()
    val labelsIds = getAll("labelsId2nd", classOf[Int])
    entity.stdLabels ++= entityDao.find(classOf[StdLabel], labelsIds)

    entity.stdTypes.clear()
    val typesIds = getAll("typesId2nd", classOf[Int])
    entity.stdTypes ++= entityDao.find(classOf[StdType], typesIds)

    entity.calendars.clear()
    val calendarIds = getAll("calendarId2nd", classOf[Int])
    entity.calendars ++= entityDao.find(classOf[Calendar], calendarIds)

    super.saveAndRedirect(entity)
  }

}
