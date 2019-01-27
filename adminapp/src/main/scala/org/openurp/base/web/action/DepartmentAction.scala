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
package org.openurp.base.web.action

import org.openurp.base.model.{ Department, School }
import org.beangle.webmvc.entity.action.RestfulAction
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order
import org.openurp.base.model.Building
import org.beangle.webmvc.api.annotation.ignore
import org.beangle.webmvc.api.view.View

class SchoolAction extends RestfulAction[School]

@action("{school}/department")
class DepartmentAction extends RestfulAction[Department] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[Department] = {
    val builder: OqlBuilder[Department] = OqlBuilder.from(entityName, "department")
    builder.where("department.school.id=:schoolId", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  @ignore
  override protected def saveAndRedirect(entity: Department): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }

}
