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

import org.beangle.commons.bean.Initializing
import org.beangle.commons.logging.Logging
import org.beangle.data.orm.hibernate.DaoJob
import org.beangle.ems.app.Ems
import org.beangle.ems.app.dao.AppDataSourceFactory
import org.openurp.base.hr.service.impl.StaffServiceImpl

class StaffAccountUpdater extends DaoJob, Logging, Initializing {
  private var staffService: StaffServiceImpl = _

  override def init(): Unit = {
    val ds = new AppDataSourceFactory()
    ds.name = "platform"
    ds.init()
    staffService = new StaffServiceImpl
    staffService.userRepo = new DefaultUserRepo(entityDao, ds.result, Ems.hostname)
    staffService.entityDao = entityDao
  }

  override def execute(): Unit = {
    logger.info("starting sync staff and teacher to account")
    staffService.createActiveUsers()
  }
}
