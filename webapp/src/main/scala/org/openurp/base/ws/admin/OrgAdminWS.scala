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

package org.openurp.base.ws.admin

import org.beangle.commons.collection.{Collections, Order}
import org.beangle.commons.json.JsonObject
import org.beangle.data.dao.{OqlBuilder, QueryPage}
import org.beangle.she.webmvc.{ExportSupport, QueryHelper}
import org.beangle.webmvc.annotation.response
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.openurp.base.model.*
import org.openurp.base.service.Feature
import org.openurp.base.web.helper.UrpUserHelper
import org.openurp.code.edu.model.{EducationLevel, EducationType}
import org.openurp.code.std.model.{StdLabel, StdType}

import java.time.LocalDate
import scala.compiletime.uninitialized

/** /api/base/admin/schools */
class SchoolWS extends AdminJsonWS[School] {

  override protected def prepare(school: School): Unit = {
    if (null == school.beginOn) school.beginOn = LocalDate.now
  }
}

/** /api/base/admin/campuses */
class CampusWS extends SchoolAdminJsonWS[Campus] {

  override protected def getQueryBuilder: OqlBuilder[Campus] = schoolScoped("campus")

  override protected def prepare(campus: Campus): Unit = {
    campus.school = getSchool
    if (!campus.persisted && null == campus.beginOn) campus.beginOn = LocalDate.now
  }
}

/** /api/base/admin/departments */
class DepartmentWS extends SchoolAdminJsonWS[Department], ExportSupport[Department] {
  var databus: DataEventBus = uninitialized

  override protected def getQueryBuilder: OqlBuilder[Department] = {
    val builder = schoolScoped("department")
    QueryHelper.addActive(builder, getBoolean("active"))
    builder
  }

  override protected def prepare(depart: Department): Unit = {
    depart.school = getSchool
    if (!depart.persisted && null == depart.beginOn) depart.beginOn = LocalDate.now
    if (formSubmitted("department.name") || formSubmitted("campusId")) {
      depart.campuses.clear()
      val campusIds = getAll("campusId", classOf[Int])
      depart.campuses ++= entityDao.find(classOf[Campus], campusIds)
    }
  }

  override protected def persist(depart: Department): JsonObject = {
    val json = super.persist(depart)
    databus.publish(DataEvent.update(depart))
    json
  }
}

/** /api/base/admin/depart-transitions */
class DepartTransitionWS extends SchoolAdminJsonWS[DepartTransition] {

  override protected def simpleEntityName: String = "transition"

  override protected def getQueryBuilder: OqlBuilder[DepartTransition] = {
    val builder = OqlBuilder.from(classOf[DepartTransition], "transition")
    builder.where("transition.from.school=:school", getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def prepare(transition: DepartTransition): Unit = {
    if (!transition.persisted && null == transition.effectiveOn) transition.effectiveOn = LocalDate.now
    if (transition.from == transition.to) {
      throw new IllegalArgumentException("请选择不同的部门")
    }
  }
}

/** /api/base/admin/projects */
class ProjectWS extends AdminJsonWS[Project] {

  override protected def prepare(project: Project): Unit = {
    if (formSubmitted("project.name")) {
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
    }
  }
}

/** /api/base/admin/project-codes */
class ProjectCodeWS extends AdminJsonWS[ProjectCode]

/** /api/base/admin/project-properties */
class ProjectPropertyWS extends AdminJsonWS[ProjectProperty] {

  override protected def prepare(property: ProjectProperty): Unit = {
    try {
      Feature.convert(property.value, property.typeName)
    } catch {
      case e: Throwable => throw new IllegalArgumentException(s"不能把${property.value}转换成${property.typeName}类型")
    }
  }
}

/** /api/base/admin/user-groups */
class UserGroupWS extends SchoolAdminJsonWS[UserGroup] {

  override protected def prepare(entity: UserGroup): Unit = {
    entity.school = getSchool
    if (!entity.persisted) entity.enabled = true
  }
}

/** /api/base/admin/users */
class UserWS extends SchoolAdminJsonWS[User] {
  var urpUserHelper: UrpUserHelper = uninitialized

  override protected def getQueryBuilder: OqlBuilder[User] = {
    val school = getSchool
    val builder = schoolScoped("user")
    val groupName = get("groupName", "")
    if (org.beangle.commons.lang.Strings.isNotEmpty(groupName)) {
      val sb = new StringBuilder()
      sb.append("user.group.name like :groupName or exists(from user.groups m where ")
      sb.append("m.group.name like :groupName and m.group.school=:school)")
      builder.where(sb.toString, "%" + groupName + "%", school)
    }
    builder
  }

  override protected def prepare(user: User): Unit = {
    user.school = getSchool
    if (formSubmitted("user.code")) {
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
    } else if (null == user.beginOn) {
      user.beginOn = LocalDate.now
    }
  }

  override protected def persist(user: User): JsonObject = {
    val json = super.persist(user)
    entityDao.refresh(user)
    urpUserHelper.createAccount(user)
    json
  }

  /** 为当前学校全部用户补建账号。 */
  @response
  def initAccount(): JsonObject = {
    val q = OqlBuilder.from(classOf[User], "u").where("u.school=:school", getSchool)
    q.orderBy("u.code")
    val pages = QueryPage(q, entityDao)
    var created = 0
    pages foreach { u =>
      urpUserHelper.createAccount(u)
      created += 1
    }
    val rs = new JsonObject()
    rs.add("created", created)
    rs
  }
}
