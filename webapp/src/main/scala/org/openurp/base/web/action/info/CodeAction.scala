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

package org.openurp.base.web.action.info

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.{ClassLoaders, Strings}
import org.beangle.commons.text.i18n.Messages
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.openurp.code.CodeBean

import java.time.LocalDate
import java.util.Locale

class CodeAction extends ActionSupport {

  var entityDao: EntityDao = _

  def index(): View = {
    val messages = Messages(Locale.SIMPLIFIED_CHINESE)
    val comments = Collections.newMap[String, String]
    val codeEntities = entityDao.domain.entities.values.filter(x => classOf[CodeBean].isAssignableFrom(x.clazz))
    codeEntities foreach { e =>
      comments.put(e.clazz.getName, messages.get(e.clazz, "class"))
    }
    val packageComments = Map("org.openurp.code.asset.model" -> "资产代码",
      "org.openurp.code.edu.model" -> "教务代码",
      "org.openurp.code.geo.model" -> "地理代码",
      "org.openurp.code.hr.model" -> "教职工代码",
      "org.openurp.code.job.model" -> "教职工代码",
      "org.openurp.code.person.model" -> "人员代码",
      "org.openurp.code.sin.model" -> "科研代码",
      "org.openurp.code.std.model" -> "学生代码",
      "org.openurp.code.trd.model" -> "教学研究代码",
    )
    val packaged = codeEntities.groupBy(x => packageComments.getOrElse(x.clazz.getPackageName, "其他代码"))
    val counts = packaged.map(x => (x._1, x._2.size))
    put("packages", packaged.keys.toBuffer.sortBy(x => counts(x)).reverse)
    put("entities", packaged)
    put("comments", comments)
    forward()
  }

  /** *缺少维护action的基础代码 */
  def testAction(): View = {
    val messages = Messages(Locale.SIMPLIFIED_CHINESE)
    val comments = Collections.newMap[String, String]
    val codeEntities = entityDao.domain.entities.values.filter(x => classOf[CodeBean].isAssignableFrom(x.clazz))
    codeEntities foreach { et =>
      var actionPackage = "org.openurp.base.web.action.admin.{m}.code"
      val packageName = et.clazz.getPackageName
      val module = Strings.substringBetween(packageName, "code.", ".model")
      actionPackage = Strings.replace(actionPackage, "{m}", module)
      val clazzName = actionPackage + "." + et.clazz.getSimpleName + "Action"
      if ClassLoaders.get(clazzName).isEmpty then
        println(clazzName)
    }

    null
  }

  def list(): View = {
    val clazzName = get("clazz", "")
    entityDao.domain.entities.get(clazzName) foreach { e =>
      if (classOf[CodeBean].isAssignableFrom(e.clazz)) {
        val query = OqlBuilder.from(e.entityName, "code")
        query.where("code.endOn is null or code.endOn >:now", LocalDate.now)
        query.orderBy("code.code")
        put("codes", entityDao.search(query))
      }
    }
    forward()
  }
}
