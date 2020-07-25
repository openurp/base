/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action

import org.beangle.data.dao.EntityDao
import org.beangle.ems.app.web.NavContext
import org.beangle.security.realm.cas.{Cas, CasConfig}
import org.beangle.webmvc.api.action.{ActionSupport, ServletSupport}
import org.beangle.webmvc.api.annotation.action
import org.beangle.webmvc.api.context.ActionContext
import org.beangle.webmvc.api.view.View
import org.openurp.base.model.School

@action("")
class IndexAction extends ActionSupport with ServletSupport {
  var casConfig: CasConfig = _

  var entityDao: EntityDao = _

  def index(): View = {
    val nav = NavContext.get(request)
    put("nav", nav)
    val school = new SchoolHelper(entityDao).getSchool(request, response)
    nav.params.put("school", school.id.toString)
    put("school", school)
    put("schools", entityDao.getAll(classOf[School]))
    forward()
  }

  def logout(): View = {
    redirect(to(Cas.cleanup(casConfig, ActionContext.current.request, ActionContext.current.response)), null)
  }

}
