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

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.beangle.commons.lang.{Numbers, Strings}
import org.beangle.commons.web.util.CookieUtils
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.api.context.Params
import org.openurp.base.model.School

class SchoolHelper(entityDao: EntityDao) {
  def getSchool(request: HttpServletRequest, response: HttpServletResponse): School = {
    val originSchoolId = getSchoolId(request)
    var sid = originSchoolId
    Params.get("schoolId") foreach { s =>
      sid = s
    }
    var s: School = null

    if (Strings.isNotEmpty(sid) && Numbers.isDigits(sid)) {
      val builder = OqlBuilder.from(classOf[School], "s")
      builder.where("s.id=:id", sid.toInt)
      builder.cacheable()
      val schools = entityDao.search(builder)
      if (schools.nonEmpty) {
        s = schools.head
      }
    }
    if (null == s) {
      val schools = entityDao.getAll(classOf[School])
      s = schools.head
    }
    if (null != s && originSchoolId != s.id.toString) {
      CookieUtils.addCookie(request, response, "school", s.id.toString, -1)
    }
    s
  }

  private def getSchoolId(request: HttpServletRequest): String = {
    CookieUtils.getCookieValue(request, "school")
  }
}
