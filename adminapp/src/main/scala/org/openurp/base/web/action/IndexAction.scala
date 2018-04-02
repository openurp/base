/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2005, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action

import org.beangle.data.dao.{ EntityDao, OqlBuilder }
import org.beangle.security.context.SecurityContext
import org.beangle.security.mgt.SecurityManager
import org.beangle.security.realm.cas.CasConfig
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.{ action, mapping }
import org.beangle.webmvc.api.view.View
import org.openurp.base.model.{ School, User }
import org.openurp.app.Urp
import org.openurp.app.UrpApp
import org.openurp.app.security.RemoteService
import org.beangle.security.Securities
import org.beangle.webmvc.api.context.ActionContext

@action("")
class IndexAction extends ActionSupport {
  var entityDao: EntityDao = _
  var casConfig: CasConfig = _

  @mapping("{school}")
  def school(): View = {
    put("menuJson", RemoteService.getMenusJson())
    put("appJson", RemoteService.getAppsJson())
    val schools = entityDao.findBy(classOf[School], "code", List(get("school").get))
    put("school", schools.head)
    put("schools", entityDao.getAll(classOf[School]))
    put("user", getUser())
    put("webappBase", Urp.webappBase)
    put("thisAppName", UrpApp.name)
    forward()
  }

  def index(): View = {
    val builder = OqlBuilder.from(classOf[School], "s").orderBy("s.beginOn desc").cacheable()
    val schools = entityDao.search(builder)
    if (schools.isEmpty) throw new RuntimeException("Cannot find any valid schools")
    redirect("school", "&school=" + schools.head.code, null)
  }

  def logout(): View = {
    redirect(to(Cas.logout(ActionContext.current.request, ActionContext.current.response)), null)
  }

  def getUser(): User = {
    val builder = OqlBuilder.from(classOf[User], "user")
    builder.where("user.code=:code", Securities.user) //.where("user.school.code=:schoolCode", get("school").get)
    val users = entityDao.search(builder)
    if (users.isEmpty) {
      throw new RuntimeException("Cannot find staff with code " + Securities.user)
    } else {
      users.head
    }
  }
}
