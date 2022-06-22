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

import java.time.LocalDate

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.ignore
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.code.hr.model.UserCategory
import org.openurp.base.model.User
import org.openurp.code.person.model.Gender

class UserAction extends RestfulAction[User] with SchoolSupport {
  override protected def getQueryBuilder: OqlBuilder[User] = {
    val builder: OqlBuilder[User] = OqlBuilder.from(entityName, "user")
    builder.where("user.school=:school",getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: User): Unit = {
    put("userCategories", entityDao.getAll(classOf[UserCategory]))
    put("genders", entityDao.getAll(classOf[Gender]))
    put("departments", getDepartments)
  }

  override protected def indexSetting(): Unit = {
    put("userCategories", entityDao.getAll(classOf[UserCategory]))
    put("departments", getDepartments)
  }

  @ignore
  override protected def saveAndRedirect(entity: User): View = {
    entity.school = getSchool
    entity.beginOn = LocalDate.now
    super.saveAndRedirect(entity)
  }
}
