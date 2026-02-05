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

package org.openurp.base.ws

import org.beangle.commons.cdi.BindModule
import org.openurp.base.service.impl.{SquadStdCountUpdater, StaffAccountUpdater}
import org.openurp.base.std.service.impl.SquadServiceImpl
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.scheduling.config.{CronTask, ScheduledTaskRegistrar}

class SchedulerModule extends BindModule {

  protected def bindTask[T <: Runnable](clazz: Class[T], expression: String): Unit = {
    val taskName = clazz.getName
    bind(taskName + "Task", classOf[CronTask]).constructor(ref(taskName), expression).lazyInit(false)
  }

  protected override def binding(): Unit = {
    bind(classOf[SquadServiceImpl])
    bind("scheduleTaskScheduler", classOf[ConcurrentTaskScheduler]).nowire()
    bind(classOf[ScheduledTaskRegistrar]).property("taskScheduler", ref("scheduleTaskScheduler"))
      .nowire()

    bind(classOf[SquadStdCountUpdater]).lazyInit(false)
    bind(classOf[StaffAccountUpdater]).lazyInit(false)
    //see CronExpression
    bindTask(classOf[SquadStdCountUpdater], "0 0 7,10,13,16,19 * * *") //every three hours
    bindTask(classOf[StaffAccountUpdater], "0 0 7,8,10,13,16,19 22 * *") //every three hours
  }
}
