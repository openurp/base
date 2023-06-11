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

import org.beangle.cdi.bind.BindModule
import org.beangle.cdi.bind.Binding.ReferenceValue
import org.beangle.commons.lang.JVM
import org.beangle.commons.lang.reflect.Reflections
import org.openurp.base.service.impl.{DaoJob, SquadServiceImpl, StaffServiceImpl}
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.scheduling.config.{CronTask, ScheduledTaskRegistrar}

import java.time.Instant
import java.util as ju
import java.util.concurrent.ScheduledThreadPoolExecutor
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class SchedulerModule extends BindModule {

  protected def bindTask[T <: Runnable](clazz: Class[T], expression: String): Unit = {
    val taskName = clazz.getName
    bind(taskName + "Task", classOf[CronTask]).constructor(ref(taskName), expression)
  }

  protected override def binding(): Unit = {
    bind(classOf[SquadServiceImpl])
    bind(classOf[StaffServiceImpl])
    bind(classOf[ConcurrentTaskScheduler]).property("scheduledExecutor", new ScheduledThreadPoolExecutor(4))
    bind(classOf[ScheduledTaskRegistrar]).nowire("triggerTasksList")

    bind(classOf[SquadStdCountUpdater])
    bind(classOf[StaffAccountUpdater])
    bindTask(classOf[SquadStdCountUpdater], "0 0 7,11,15 * * *")
    bindTask(classOf[StaffAccountUpdater], "0 0 7 * * *")
  }

}

class SquadStdCountUpdater extends DaoJob {
  var squadService: SquadServiceImpl = _

  override def execute(): Unit = {
    squadService.autoStatStdCount()
  }
}

class StaffAccountUpdater extends DaoJob {
  var staffService: StaffServiceImpl = _

  override def execute(): Unit = {
    staffService.createActiveUsers()
  }
}