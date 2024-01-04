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

package org.openurp.base.web.helper

import org.beangle.commons.bean.Initializing
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.ems.app.Ems
import org.beangle.ems.app.datasource.AppDataSourceFactory
import org.openurp.base.hr.model.{Staff, Teacher}
import org.openurp.base.model.User
import org.openurp.base.service.UserRepo
import org.openurp.base.service.impl.DefaultUserRepo

class UrpUserHelper extends Initializing {

  var entityDao: EntityDao = _
  var userRepo: UserRepo = _

  override def init(): Unit = {
    val ds = new AppDataSourceFactory()
    ds.name = "platform"
    ds.init()
    userRepo = new DefaultUserRepo(entityDao, ds.result, Ems.hostname)
  }

  def createStaffUser(staff: Staff, oldCode: Option[String]): User = {
    userRepo.createUser(staff, oldCode)
  }

  def createTeacherUser(teacher: Teacher): User = {
    userRepo.createUser(teacher)
  }

  def createAccount(user: User): Unit = {
    userRepo.createAccount(user)
  }

  def createActiveUsers(): Unit = {
    val query = OqlBuilder.from(classOf[Teacher], "teacher")
    query.where("teacher.endOn is null")
    val teachers = entityDao.search(query)
    teachers.foreach { teacher =>
      userRepo.createUser(teacher)
    }

    val query2 = OqlBuilder.from(classOf[Staff], "staff")
    query2.where("staff.endOn is null")
    query2.where(s"not exists(from ${classOf[Teacher].getName} t where t.staff=staff)")
    val staffs = entityDao.search(query2)
    staffs.foreach { staff =>
      userRepo.createUser(staff, None)
    }
  }
}
