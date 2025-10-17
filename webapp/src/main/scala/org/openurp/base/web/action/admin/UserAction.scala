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

import org.beangle.commons.collection.{Collections, Order}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{OqlBuilder, QueryPage}
import org.beangle.webmvc.annotation.ignore
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.view.View
import org.openurp.base.model.{User, UserGroup}
import org.openurp.base.web.helper.UrpUserHelper
import org.openurp.code.hr.model.UserCategory
import org.openurp.code.person.model.Gender

import java.time.LocalDate

class UserAction extends RestfulAction[User] with SchoolSupport {
  var urpUserHelper: UrpUserHelper = _

  override protected def getQueryBuilder: OqlBuilder[User] = {
    val school = getSchool
    val builder = OqlBuilder.from(classOf[User], "user")
    builder.where("user.school=:school", school)
    populateConditions(builder)
    //查询用户组
    val groupName = get("groupName", "")
    if (Strings.isNotEmpty(groupName)) {
      val sb = new StringBuilder()
      sb.append("user.group.name like :groupName or exists(from user.groups m where ")
      sb.append("m.group.name like :groupName and m.group.school=:school)")
      val params = new collection.mutable.ListBuffer[Object]
      params += ("%" + groupName + "%")
      params += school
      builder.where(sb.toString, params.toSeq: _*)
    }

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
    var defaultGroup = List.empty[UserGroup]
    getInt("user.group.id") foreach { groupId =>
      defaultGroup = entityDao.find(classOf[UserGroup], groupId).toList
    }
    val groupIds = getAll("group.id", classOf[Int])
    val newGroups = entityDao.find(classOf[UserGroup], groupIds)
    val allGroups = Collections.newBuffer[UserGroup]
    allGroups.addAll(defaultGroup)
    allGroups.addAll(newGroups)
    user.updateGroups(allGroups)

    entityDao.saveOrUpdate(user)
    entityDao.refresh(user)
    urpUserHelper.createAccount(user)
    super.saveAndRedirect(user)
  }

  /** 创建账号 */
  def initAccount(): View = {
    val school = getSchool
    val q = OqlBuilder.from(classOf[User], "u").where("u.school=:school", school)
    q.orderBy("u.code")
    val pages = QueryPage(q, entityDao)
    pages foreach { u =>
      urpUserHelper.createAccount(u)
    }
    redirect("search", "初始化完毕")
  }
}
