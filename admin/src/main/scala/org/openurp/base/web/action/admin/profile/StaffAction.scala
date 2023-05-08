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

package org.openurp.base.web.action.admin.profile

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.context.Params
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.support.helper.{PopulateHelper, QueryHelper}
import org.openurp.base.edu.model.Teacher
import org.openurp.base.model.{Department, Project, Staff}
import org.openurp.base.profile.model.StaffProfile
import org.openurp.code.edu.model.{Degree, EducationDegree}
import org.openurp.code.job.model.ProfessionalTitle
import org.openurp.starter.web.support.ProjectSupport

import java.time.Instant

class StaffAction extends RestfulAction[StaffProfile], ProjectSupport {

  override def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", getDeparts)
    super.indexSetting()
  }

  override def simpleEntityName: String = {
    "profile"
  }

  override def editSetting(entity: StaffProfile): Unit = {
    given project: Project = getProject

    val staff = entity.staff
    put("staff", staff)
    put("departs", getDeparts)
    put("titles", getCodes(classOf[ProfessionalTitle]))
    put("degrees", getCodes(classOf[Degree]))
    put("eduDegrees", getCodes(classOf[EducationDegree]))
    super.editSetting(entity)
  }

  def missings(): View = {
    given project: Project = getProject

    val query = OqlBuilder.from(classOf[Staff], "staff")
    query.where("staff.school=:school", project.school)
    query.where(s"not exists(from ${classOf[StaffProfile].getName} p where p.staff=staff)")
    populateConditions(query)
    val sort = Params.get(Order.OrderStr) match {
      case orderBy@Some(_) => orderBy
      case None => Params.get("sort")
    }
    sort foreach { orderClause =>
      if orderClause.startsWith("staff") then query.orderBy(orderClause)
    }
    query.limit(getPageLimit)
    put("staffs", entityDao.search(query))
    forward()
  }

  def newProfile(): View = {
    given project: Project = getProject

    val staff = entityDao.get(classOf[Staff], getLongId("staff"))
    val profile = new StaffProfile
    profile.staff = staff
    put("profile", profile)
    put("staff", staff)
    put("departs", getDeparts)
    put("titles", getCodes(classOf[ProfessionalTitle]))
    put("degrees", getCodes(classOf[Degree]))
    put("eduDegrees", getCodes(classOf[EducationDegree]))
    forward("form")
  }

  override def saveAndRedirect(profile: StaffProfile): View = {
    val staff = entityDao.get(classOf[Staff], profile.staff.id)
    PopulateHelper.populate(staff, Params.sub("staff"))
    entityDao.saveOrUpdate(staff, profile)
    profile.updatedAt = Instant.now
    super.saveAndRedirect(profile)
  }

}
