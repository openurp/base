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

package org.openurp.base.web.action.admin

import org.beangle.commons.collection.Collections
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.code.EducationType
import org.openurp.base.model.*
import org.openurp.base.std.code.{StdLabel, StdType}
import org.openurp.code.edu.model.{EduCategory, EducationLevel}
import org.openurp.code.service.CodeService

import scala.collection.mutable.Buffer

class ProjectAction extends RestfulAction[Project] {

  var codeService: CodeService = _

  override def editSetting(project: Project): Unit = {
    val schools = entityDao.getAll(classOf[School])
    var school = project.school
    if (null == school) {
      school = schools.head
    }
    put("schools", schools)
    val calendars = findInSchool(classOf[Calendar], school)
    put("calendars", calendars)

    put("campuses", findInSchool(classOf[Campus], school))
    put("departments", findInSchool(classOf[Department], school))
    put("levels", codeService.get(classOf[EducationLevel]))
    put("stdLabels", codeService.get(classOf[StdLabel]))
    put("stdTypes", codeService.get(classOf[StdType]))
    put("eduCategories", codeService.get(classOf[EduCategory]))
    put("eduTypes", codeService.get(classOf[EducationType]))
    super.editSetting(project)
  }

  private def findInSchool[T <: Entity[_]](clazz: Class[T], school: School): Buffer[T] = {
    val query = OqlBuilder.from(clazz, "aa")
    query.where("aa.school=:school", school)
    entityDao.search(query).toBuffer
  }

  protected override def saveAndRedirect(project: Project): View = {
    val ids = getAll("campus.id", classOf[Int])
    val newCampuses = entityDao.find(classOf[Campus], getIntIds("campus"))
    project.campuses --= Collections.subtract(project.campuses, newCampuses)
    project.campuses ++= Collections.subtract(newCampuses, project.campuses)

    project.departments.clear()
    project.departments ++= entityDao.find(classOf[Department], getIntIds("department"))

    val newLevels = entityDao.find(classOf[EducationLevel], getIntIds("level"))
    project.levels --= Collections.subtract(project.levels, newLevels)
    project.levels ++= Collections.subtract(newLevels, project.levels)

    val newLabels = entityDao.find(classOf[StdLabel], getIntIds("stdLabel"))
    project.stdLabels --= Collections.subtract(project.stdLabels, newLabels)
    project.stdLabels ++= Collections.subtract(newLabels, project.stdLabels)

    val newTypes = entityDao.find(classOf[StdType], getIntIds("stdType"))
    project.stdTypes --= Collections.subtract(project.stdTypes, newTypes)
    project.stdTypes ++= Collections.subtract(newTypes, project.stdTypes)


    val newEduTypes = entityDao.find(classOf[EducationType], getIntIds("eduType"))
    project.eduTypes --= Collections.subtract(project.eduTypes, newEduTypes)
    project.eduTypes ++= Collections.subtract(newEduTypes, project.eduTypes)

    super.saveAndRedirect(project)
  }

  private def findInProject[T <: Entity[_]](clazz: Class[T], project: Project): Buffer[T] = {
    val query = OqlBuilder.from(clazz, "aa")
    query.where("aa.school=:project", project)
    entityDao.search(query).toBuffer
  }

}
