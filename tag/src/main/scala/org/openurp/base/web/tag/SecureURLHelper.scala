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

package org.openurp.base.web.tag

import jakarta.servlet.http.HttpServletRequest
import org.beangle.ems.app.Ems
import org.beangle.security.Securities
import org.beangle.web.servlet.util.RequestUtils
import org.beangle.webmvc.context.ActionContext

object SecureURLHelper {

  def appendSessionId(url: String): String = {
    if (url == null) return null
    val origin = getOrigin(ActionContext.current.request)
    val sameSite = url.startsWith(origin)
    if (sameSite) {
      url
    } else {
      val hasParams = url.contains("?")
      url + ((if (hasParams) "&" else "?") + s"${Ems.sid.name}=" + Securities.session.get.id)
    }
  }

  private def getOrigin(request: HttpServletRequest): String = {
    var s = request.getAttribute("_origin_").asInstanceOf[String]
    if (s == null) {
      s = RequestUtils.getOrigin(request)
      request.setAttribute("_origin_", s)
    }
    s
  }
}
