/*
 * Copyright (C) 2005, The OpenURP Software.
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

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.execution.Handler
import org.openurp.base.code.model.UserCategory
import org.openurp.base.edu.code.model.TeacherType
import org.openurp.base.edu.model.Teacher
import org.openurp.base.model.{Department, Name, Person, User}
import org.openurp.base.web.helper.{QueryHelper, URPUserCategory, UrpUserHelper}
import org.openurp.code.edu.model.{Degree, EducationDegree}
import org.openurp.code.hr.model.WorkStatus
import org.openurp.code.job.model.ProfessionalTitle
import org.openurp.code.person.model.{Gender, IdType}

import java.time.Instant

class TeacherAction extends ProjectRestfulAction[Teacher] {

  var urpUserHelper: Option[UrpUserHelper] = None

  override def getQueryBuilder: OqlBuilder[Teacher] = {
    QueryHelper.addTemporalOn(super.getQueryBuilder, getBoolean("active"))
  }

  override def editSetting(entity: Teacher) = {
    put("departments", findInSchool(classOf[Department]))
    put("genders", entityDao.getAll(classOf[Gender]))
    put("idTypes", entityDao.getAll(classOf[IdType]))
    put("teacherTypes", entityDao.getAll(classOf[TeacherType]))
    put("professionalTitles", entityDao.getAll(classOf[ProfessionalTitle]))
    put("degrees", entityDao.getAll(classOf[Degree]))
    put("educationDegrees", entityDao.getAll(classOf[EducationDegree]))
    put("statuses", entityDao.getAll(classOf[WorkStatus]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(teacher: Teacher): View = {
    val p = getProject
    teacher.projects += p

    var createUser = false
    var user = populateEntity(classOf[User], "user")
    val school = p.school
    if (!user.persisted) {
      val userQuery = OqlBuilder.from(classOf[User], "user").where("user.code=:code", user.code).where("user.school =:school", school)
      val users = entityDao.search(userQuery)
      if (users.size == 1) {
        user = users.head
      } else {
        user.school = school
        createUser = true
        user.category = new UserCategory
        user.category.id = URPUserCategory.Teacher
      }
    }
    user.beginOn = teacher.beginOn
    user.endOn = teacher.endOn
    user.updatedAt = Instant.now

    var person = populateEntity(classOf[Person], "person")
    if (!person.persisted && Strings.isNotEmpty(person.code)) {
      val people = entityDao.findBy(classOf[Person], "code", List(person.code))
      if (people.size == 1) {
        person = people.head
      }
    }
    teacher.user = user
    teacher.department = user.department
    teacher.updatedAt = Instant.now
    teacher.school = user.school
    val project = getProject
    if (!teacher.projects.contains(project)) {
      teacher.projects.add(project)
    }
    try {
      if (Strings.isNotEmpty(person.code) && null != person.birthday) {
        teacher.person = Some(person)
        if (null == person.name) {
          person.name = new Name
        }
        person.name.formatedName = user.name
        person.updatedAt = Instant.now
        person.gender = user.gender
        entityDao.saveOrUpdate(user, person, teacher)
      } else {
        entityDao.saveOrUpdate(user, teacher)
      }
      if (createUser) {
        urpUserHelper foreach { helper =>
          helper.createTeacherUser(teacher)
        }
      }
      redirect("search", "info.save.success")

    } catch {
      case e: Exception => {
        val redirectTo = Handler.mapping.method.getName match {
          case "save" => "editNew"
          case "update" => "edit"
        }
        logger.info("save forwad failure", e)
        redirect(redirectTo, "info.save.failure")
      }
    }
  }

  override protected def indexSetting(): Unit = {
    put("departments", findInSchool(classOf[Department]))
    put("teacherTypes", entityDao.getAll(classOf[TeacherType]))
  }

}
