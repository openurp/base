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

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.ignore
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.{Campus, Department, School}
import org.openurp.code.edu.model.Institution
import org.openurp.code.hr.model.DepartmentCategory

class SchoolAction extends RestfulAction[School] {
  override def editSetting(entity: School): Unit = {
    super.editSetting(entity)
    put("institutions", entityDao.getAll(classOf[Institution]))
  }
}

class DepartmentAction extends RestfulAction[Department] with SchoolSupport {
  override protected def getQueryBuilder: OqlBuilder[Department] = {
    val builder: OqlBuilder[Department] = OqlBuilder.from(entityName, "department")
    builder.where("department.school=:school", getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override def editSetting(depart: Department): Unit = {
    val school = getSchool
    super.editSetting(depart)
    val query = OqlBuilder.from(classOf[Campus], "c")
    query.where("c.school=:school", school)
    put("campuses", entityDao.search(query))
    put("categories", entityDao.getAll(classOf[DepartmentCategory]))
    val parents = entityDao.findBy(classOf[Department], "school", school).toBuffer
    if (depart.persisted) parents.subtractOne(depart)
    put("parents", parents)
  }

  @ignore
  override protected def saveAndRedirect(depart: Department): View = {
    depart.school = getSchool
    depart.campuses.clear()
    val campusIds = getAll("campusId", classOf[Int])
    depart.campuses ++= entityDao.find(classOf[Campus], campusIds)
    super.saveAndRedirect(depart)
  }

}
