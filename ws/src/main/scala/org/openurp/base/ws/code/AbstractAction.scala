package org.openurp.base.ws.code

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.dao.EntityDao
import org.beangle.webmvc.api.action.EntityActionSupport
import org.beangle.webmvc.api.annotation.response
import org.openurp.code.BaseCode

class AbstractAction[T <: BaseCode] extends EntityActionSupport[T] {

  var entityDao: EntityDao = _

  @response
  def index(): Seq[T] = {
    val builder = OqlBuilder.from(entityType, "code")
    builder.orderBy(get("orderBy", "code.code"))
    builder.select("new org.openurp.services.kernel.base.code.model.Code(code.id,code.code,code.name)")
    buildQuery(builder)
    entityDao.search(builder)
  }

  def buildQuery(builder: OqlBuilder[T]): Unit = {

  }
}