package org.openurp.base.action.code

import org.beangle.webmvc.entity.action.EntityDrivenAction
import org.beangle.webmvc.entity.action.RestfulAction
import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.openurp.base.domain.code.CodeCategory

class CategoryAction extends RestfulAction[CodeCategory] {

  override def editSetting(entity: CodeCategory) = {
    val query = OqlBuilder.from(classOf[CodeCategory])
    query.orderBy("name")
    val categorys = entityDao.search(query)
    put("categorys", categorys)
    super.editSetting(entity)
  }
}