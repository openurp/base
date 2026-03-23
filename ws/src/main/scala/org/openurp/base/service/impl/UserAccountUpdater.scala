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

package org.openurp.base.service.impl

import org.beangle.commons.bean.{Initializing, Scheduled}
import org.beangle.commons.lang.time.Stopwatch
import org.beangle.commons.logging.Logging
import org.beangle.data.dao.{OqlBuilder, QueryPage}
import org.beangle.data.orm.AbstractDaoTask
import org.beangle.ems.app.Ems
import org.beangle.ems.app.dao.AppDataSourceFactory
import org.openurp.base.hr.model.Staff
import org.openurp.base.model.Department
import org.openurp.base.std.model.Student

import java.time.LocalDate

class UserAccountUpdater extends AbstractDaoTask, Logging, Initializing, Scheduled {
  private var userRepo: DefaultUserRepo = _
  var expression: String = _

  override def init(): Unit = {
    val ds = new AppDataSourceFactory()
    ds.name = "platform"
    ds.init()
    userRepo = new DefaultUserRepo(entityDao, ds.getObject, Ems.hostname)
  }

  override def execute(): Unit = {
    val bulkSize = 500
    logger.info("starting sync departs to ems")
    //创建所有的部门
    entityDao.findBy(classOf[Department], "school.id" -> userRepo.orgId) foreach { depart =>
      userRepo.createDepart(depart)
    }

    var i = 0
    logger.info("starting sync staff and teacher to account")
    val query1 = OqlBuilder.from(classOf[Staff], "staff")
    query1.where("staff.endOn is null or staff.endOn >=:now", LocalDate.now)
    query1.where(s"staff.school.id=:schoolId", userRepo.orgId)
    val page1 = QueryPage(query1, entityDao)
    page1 foreach { staff =>
      userRepo.createUser(staff, None)
      i += 1
      if i % bulkSize == 0 then clean()
    }

    logger.info("starting sync student user to account")
    i = 0
    val sw = new Stopwatch(true)
    val query2 = OqlBuilder.from(classOf[Student], "std")
    query2.where("std.project.school.id=:schoolId", userRepo.orgId)
    query2.where("std.endOn is null or std.endOn >=:now", LocalDate.now)
    query2.orderBy("std.code").limit(1, bulkSize)
    val page2 = QueryPage(query2, entityDao)
    page2 foreach { std =>
      userRepo.createUser(std, std.user.code, None)
      i += 1
      if i % bulkSize == 0 then clean()
    }
    logger.info(s"starting sync ${i} student user to platform using ${sw}")
  }

}
