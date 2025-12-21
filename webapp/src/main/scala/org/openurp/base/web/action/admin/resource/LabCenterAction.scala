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

package org.openurp.base.web.action.admin.resource

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.view.View
import org.openurp.base.model.{Department, Project}
import org.openurp.base.resource.model.LabCenter
import org.openurp.base.web.action.admin.ProjectRestfulAction

import java.time.LocalDate

class LabCenterAction extends ProjectRestfulAction[LabCenter] {

  override def getQueryBuilder: OqlBuilder[LabCenter] = {
    val builder = super.getQueryBuilder
    builder.where("labCenter.school=:school", getProject.school)
    builder
  }

  override protected def saveAndRedirect(center: LabCenter): View = {
    if (null == center.beginOn) center.beginOn = LocalDate.now

    val departIds = getAll("depart.id", classOf[Int])
    val newDeparts = entityDao.find(classOf[Department], departIds)

    val project = getProject
    center.departs.clear()
    center.departs.addAll(newDeparts)
    center.school = project.school
    saveMore(center)
    redirect("search", "info.save.success")
  }

  override def editSetting(center: LabCenter): Unit = {
    given project: Project = getProject

    if (null == center.school) {
      center.school = project.school
    }
    if (!center.persisted) {
      center.beginOn = LocalDate.now()
    }
    put("departs", project.departments)
  }
}
