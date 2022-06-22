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

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.{Chars, ClassLoaders}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.annotation.{action, mapping, param, response}
import org.openurp.code.Code
import org.openurp.code.service.CodeService

@action("code")
class CodeWS extends ActionSupport {
  var codeService: CodeService = _

  var clazzes = Collections.newMap[String, Class[_ <: Code]]

  @response
  @mapping("{code}")
  def index(@param("code") code: String): Any = {
    put("properties", List(classOf[Code] -> List("id", "name", "code", "enName")))
    codeService.get(load(camel(code)))
  }

  /**
   * load class
   * @param shortName
   * @return
   */
  private def load(shortName: String): Class[_ <: Code] = {
    clazzes.get(shortName) match {
      case Some(clz) => clz
      case None =>
        if (loadClass("org.openurp.code.edu.model." + shortName).isEmpty) {
          if (loadClass("org.openurp.code.person.model." + shortName).isEmpty) {
            if (loadClass("org.openurp.code.hr.model." + shortName).isEmpty) {
              if (loadClass("org.openurp.code.job.model." + shortName).isEmpty) {
                if (loadClass("org.openurp.code.sin.model." + shortName).isEmpty) {
                  if (loadClass("org.openurp.code.asset.model." + shortName).isEmpty) {
                    if (loadClass("org.openurp.code.geo.model." + shortName).isEmpty) {
                      if (loadClass("org.openurp.code.std.model." + shortName).isEmpty) {
                        if (loadClass("org.openurp.base.edu.code." + shortName).isEmpty) {
                          throw new RuntimeException("cannot find code " + shortName)
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        clazzes(shortName)
    }
  }

  /**
   * load class and put in cache
   * @param className
   * @return
   */
  private def loadClass(className: String): Option[Class[_ <: Code]] = {
    ClassLoaders.get(className) match {
      case Some(r) =>
        val clz = r.asInstanceOf[Class[_ <: Code]]
        clazzes.put(clz.getSimpleName, clz)
        Some(clz)
      case None => None
    }
  }

  /**
   * course-categories => CourseCategory
   * @param code
   * @return
   */
  private def camel(code: String): String = {
    var cn = code
    if (cn.endsWith("ies")) {
      cn = cn.substring(0, cn.length - 3) + "y"
    } else if (cn.endsWith("ses")) {
      cn = cn.substring(0, cn.length - 2)
    } else if (cn.endsWith("es") || cn.endsWith("s")) {
      cn = cn.substring(0, cn.length - 1)
    }
    var i = 0
    val chars = cn.toCharArray()
    val results = Collections.newBuffer[Char]
    var captilize = true
    while (i < chars.length) {
      val c = chars(i)
      if (Chars.isAsciiAlpha(c)) {
        results += (if (captilize) c.toUpper else c)
        captilize = false
      } else {
        captilize = true
      }
      i += 1
    }
    new String(results.toArray)
  }
}
