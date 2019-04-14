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

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.action.ServletSupport
import org.beangle.webmvc.entity.action.EntityAction
import org.openurp.base.model.{Department, School}

trait SchoolSupport extends ServletSupport {
  this: EntityAction[_] =>

  def getSchool: School = {
    val originSchoolCode = schoolCode
    var sc = originSchoolCode
    var s: School = null
    while (null == s && Strings.isNotEmpty(sc)) {
      val builder = OqlBuilder.from(classOf[School], "s")
      builder.where("s.code=:code", sc)
      builder.cacheable()
      val schools = entityDao.search(builder)
      if (schools.nonEmpty) {
        s = schools.head
      } else {
        sc = Strings.substringAfter(sc, ".")
      }
    }
    if (null == s) {
      val schools = entityDao.getAll(classOf[School])
      if (schools.size == 1) {
        s = schools.head
      }
    }
    if (null != s && originSchoolCode != s.code) {
      addCookie("school", s.code, -1)
    }
    s
  }

  private def schoolCode: String = {
    val p = getCookieValue("school")
    if (null == p) {
      request.getServerName
    } else {
      p
    }
  }

  def getDepartments: Seq[Department] = {
    entityDao.search(OqlBuilder.from(classOf[Department], "c").where("c.school=:school", getSchool))
  }
}
