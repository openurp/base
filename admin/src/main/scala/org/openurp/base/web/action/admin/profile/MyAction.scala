/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.admin.profile

import org.beangle.data.dao.OqlBuilder
import org.beangle.security.Securities
import org.beangle.web.action.context.Params
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.support.helper.PopulateHelper
import org.openurp.base.model.{Department, Staff}
import org.openurp.base.profile.model.StaffProfile
import org.openurp.code.edu.model.{Degree, EducationDegree}
import org.openurp.code.job.model.ProfessionalTitle
import org.openurp.starter.web.support.ProjectSupport

import java.time.Instant

class MyAction extends RestfulAction[StaffProfile], ProjectSupport {

  override def index(): View = {
    getStaff match {
      case Some(t) =>
        val builder = OqlBuilder.from(classOf[StaffProfile], "tb")
        builder.where("tb.staff=:staff", t)
        val profiles = entityDao.search(builder)
        if (profiles.isEmpty) {
          redirect("editNew")
        } else {
          put("profile", profiles.head)
          forward("info")
        }
      case None =>
        forward("notteacher")
    }
  }

  override def editSetting(entity: StaffProfile): Unit = {
    val staff = getStaff.get
    put("staff", staff)
    val query = OqlBuilder.from(classOf[Department], "d")
    query.where("d.school=:school", staff.school)
    query.where("d.endOn is null")
    query.orderBy("d.code")
    put("departs", entityDao.search(query))
    put("titles", codeService.get(classOf[ProfessionalTitle]))
    put("degrees", codeService.get(classOf[Degree]))
    put("eduDegrees", codeService.get(classOf[EducationDegree]))
    super.editSetting(entity)
  }

  override def simpleEntityName: String = {
    "profile"
  }

  override def saveAndRedirect(profile: StaffProfile): View = {
    profile.staff = getStaff.head
    PopulateHelper.populate(profile.staff, Params.sub("staff"))
    entityDao.saveOrUpdate(profile.staff, profile)
    profile.updatedAt = Instant.now
    redirect("index", "info.save.success")
  }

  private def getStaff: Option[Staff] = {
    val tQuery = OqlBuilder.from(classOf[Staff], "t")
    tQuery.where("t.code=:code", Securities.user)
    entityDao.search(tQuery).headOption
  }
}
