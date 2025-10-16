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
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.{Major, MinorMajor}
import org.openurp.base.model.Project
import org.openurp.code.edu.model.Institution
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

/** 辅修专业
 */
class MinorMajorAction extends RestfulAction[MinorMajor], ProjectSupport {

  override def simpleEntityName: String = "major"

  override def indexSetting(): Unit = {
    given project: Project = getProject

    put("institutions", getCodes(classOf[Institution]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(m: MinorMajor): View = {
    m.project = getProject
    super.saveAndRedirect(m)
  }

  override def getQueryBuilder: OqlBuilder[MinorMajor] = {
    val query = super.getQueryBuilder
    query.where("major.project = :project", getProject)
    query
  }

  override def editSetting(entity: MinorMajor): Unit = {
    given project: Project = getProject

    put("institutions", getCodes(classOf[Institution]))
    put("departments", getDeparts)
    super.editSetting(entity)
  }

  /** 导入本项目的所有专业
   *
   * @return
   */
  def init(): View = {
    val project = getProject
    val existsMajors = entityDao.findBy(classOf[MinorMajor], "project", project).groupBy(_.name)
    val majors = entityDao.findBy(classOf[Major], "project", project)
    majors foreach { major =>
      if (!existsMajors.contains(major.name) && major.within(LocalDate.now)) {
        val newMajor = new MinorMajor
        newMajor.name = major.name
        newMajor.project = project
        newMajor.beginOn = major.beginOn
        newMajor.major = Some(major)
        newMajor.department = major.departmentsNow.headOption
        newMajor.institution = project.school.institution
        entityDao.saveOrUpdate(newMajor)
      }
    }
    redirect("search", "导入成功")
  }
}
