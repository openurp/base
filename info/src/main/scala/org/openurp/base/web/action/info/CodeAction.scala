package org.openurp.base.web.action.info

import org.beangle.commons.collection.Collections
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
    val packageComments = Map("org.openurp.base.edu.code" -> "教学代码",
      "org.openurp.base.std.code" -> "学生代码",
      "org.openurp.code.asset.model" -> "资产代码",
      "org.openurp.code.edu.model" -> "教学代码",
      "org.openurp.code.geo.model" -> "地理代码",
      "org.openurp.code.hr.model" -> "教职工代码",
      "org.openurp.code.job.model" -> "教职工代码",
      "org.openurp.code.person.model" -> "人员代码",
      "org.openurp.code.sin.model" -> "科研代码",
      "org.openurp.code.std.model" -> "学生代码"
    )
    val packaged = codeEntities.groupBy(x => packageComments(x.clazz.getPackageName))
    val counts = packaged.map(x => (x._1, x._2.size))
    put("packages", packaged.keys.toBuffer.sortBy(x => counts(x)).reverse)
    put("entities", packaged)
    put("comments", comments)
    forward()
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
