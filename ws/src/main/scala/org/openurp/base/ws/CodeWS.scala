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

import org.beangle.commons.bean.Initializing
import org.beangle.commons.collection.Collections
import org.beangle.commons.json.JsonObject
import org.beangle.commons.lang.Strings.unCamel
import org.beangle.commons.lang.{Chars, ClassLoaders, Strings}
import org.beangle.commons.text.inflector.en.EnNounPluralizer
import org.beangle.data.dao.EntityDao
import org.beangle.data.json.JsonAPI
import org.beangle.data.orm.OrmEntityType
import org.beangle.webmvc.annotation.{action, mapping, param, response}
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.util.CacheControl
import org.openurp.code.Code
import org.openurp.code.service.CodeService

@action("code")
class CodeWS extends ActionSupport, Initializing {
  var codeService: CodeService = _
  var entityDao: EntityDao = _
  private var clazzes: Map[String, Class[_ <: Code]] = _

  override def init(): Unit = {
    val entities = entityDao.domain.entities
    val codeEntities = entities.map(_._2.asInstanceOf[OrmEntityType]).filter(x => classOf[Code].isAssignableFrom(x.clazz))
    clazzes = codeEntities.map(oet => (nomalize(oet.entityName), oet.clazz.asSubclass(classOf[Code]))).toMap
  }

  private def nomalize(name: String): String = {
    val loc = name.lastIndexOf('.')
    val shortName = if (loc < 0) name else name.substring(loc + 1)
    EnNounPluralizer.pluralize(unCamel(shortName, '-'))
  }

  @response(cacheable = true )
  @mapping("{code}")
  def index(@param("code") code: String): JsonObject = {
    val datas = clazzes.get(code) match {
      case Some(clazz) => codeService.get(clazz)
      case None => List.empty
    }
    val context = JsonAPI.context(ActionContext.current.params)
    context.mkJson(datas, "id", "code", "name", "enName")
  }
}
