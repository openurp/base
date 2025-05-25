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

package org.openurp.base.web.helper

import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.net.http.HttpUtils

object TextbookHelper {

  def fetchByIsbn(isbn: String): JsonObject = {
    val url = s"http://tool.openurp.net/book/isbn/${isbn}.json"
    val res = HttpUtils.getText(url)
    val r = Json.parse(res.getText).asInstanceOf[JsonObject]
    if (r.getBoolean("success")) {
      val j = r.get("data").map(_.asInstanceOf[JsonObject])
      j.map { jd =>
        val data = new JsonObject()
        data.add("isbn", jd.getString("isbn"))
        data.add("name", jd.getString("name"))
        data.add("author", jd.getString("author"))
        data.add("press", jd.getString("press"))
        data.add("publishedOn", jd.getString("publishedOn"))
        data.add("edition", jd.getString("edition"))
        data.add("description", jd.getString("description"))
        data
      }.get
    } else {
      val d = new JsonObject()
      d.add("isbn", isbn)
    }
  }
}
