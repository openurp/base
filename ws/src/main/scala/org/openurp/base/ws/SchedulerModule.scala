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
import org.openurp.base.service.impl.{SquadStdCountUpdater, UserAccountUpdater}
import org.openurp.base.std.service.impl.SquadServiceImpl

class SchedulerModule extends BindModule {

  protected override def binding(): Unit = {
    //定时任务不能按需初始化，需要随系统一起启动
    wiredEagerly(true)
    bind(classOf[SquadServiceImpl])

    //every seven hours
    bind(classOf[SquadStdCountUpdater]).property("expression", "0 0 7,14 * * *")

    //every seven hours
    bind(classOf[UserAccountUpdater]).property("expression", "0 0 7,14 * * *")

  }
}
