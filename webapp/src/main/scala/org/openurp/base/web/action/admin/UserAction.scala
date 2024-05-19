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
import org.openurp.base.model.{User, UserGroup}
import org.openurp.base.web.helper.UrpUserHelper
import org.openurp.code.hr.model.UserCategory
import org.openurp.code.person.model.Gender

import java.time.LocalDate

class UserAction extends RestfulAction[User] with SchoolSupport {
  var urpUserHelper: UrpUserHelper = _

  override protected def getQueryBuilder: OqlBuilder[User] = {
    val builder = OqlBuilder.from(classOf[User], "user")
    builder.where("user.school=:school", getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: User): Unit = {
    put("userCategories", entityDao.getAll(classOf[UserCategory]))
    put("genders", entityDao.getAll(classOf[Gender]))
    put("userGroups", entity.groups.map(_.group))
    put("groups", entityDao.findBy(classOf[UserGroup], "school", getSchool))
    put("departments", getDepartments)
  }

  override protected def indexSetting(): Unit = {
    put("userCategories", entityDao.getAll(classOf[UserCategory]))
    put("departments", getDepartments)
  }

  @ignore
  override protected def saveAndRedirect(user: User): View = {
    user.school = getSchool
    user.beginOn = LocalDate.now
    val groupIds = getAll("group.id", classOf[Int])
    val newGroups = entityDao.find(classOf[UserGroup], groupIds)
    user.groups.clear()
    user.addGroups(newGroups)
    entityDao.saveOrUpdate(user)

    urpUserHelper.createAccount(user)
    super.saveAndRedirect(user)
  }
}
