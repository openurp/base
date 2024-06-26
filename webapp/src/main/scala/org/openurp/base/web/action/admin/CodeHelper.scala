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

package org.openurp.base.web.action.admin

import org.beangle.commons.bean.Initializing
import org.beangle.commons.bean.orderings.CollatorOrdering
import org.beangle.commons.lang.Strings.unCamel
import org.beangle.commons.text.i18n.Messages
import org.beangle.data.dao.EntityDao
import org.beangle.data.model.meta.EntityType
import org.beangle.data.orm.OrmEntityType
import org.openurp.code.Code

import java.util.Locale

class CodeHelper extends Initializing {
  var entityDao: EntityDao = _
  private var metas: Map[String, CodeMeta] = _

  override def init(): Unit = {
    val entities = entityDao.domain.entities
    val messages = Messages(Locale.SIMPLIFIED_CHINESE)
    val codeEntities = entities.map(_._2.asInstanceOf[OrmEntityType]).filter(x => classOf[Code].isAssignableFrom(x.clazz))
    metas = codeEntities.map { e =>
      val code = unCamel(e.clazz.getSimpleName, '-')
      val name = messages.get(e.clazz, "class")
      (code, CodeMeta(code, name, e))
    }.toMap
  }

  def getMeta(name: String): Option[CodeMeta] = {
    metas.get(name)
  }

  def loadMeta(name: String): CodeMeta = {
    metas(name)
  }

  def getMetas(packageNames: collection.Set[String]): collection.Seq[CodeMeta] = {
    given ordering: Ordering[String] = new CollatorOrdering(true)

    metas.values.filter(x => packageNames.contains(x.entityType.clazz.getPackageName)).toBuffer.sortBy(_.name)
  }

}

case class CodeMeta(code: String, name: String, entityType: EntityType)
