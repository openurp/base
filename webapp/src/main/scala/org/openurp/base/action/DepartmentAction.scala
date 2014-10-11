package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.Institution
import org.openurp.base.domain.DepartmentBean
import org.openurp.base.domain.SchoolBean
import org.openurp.base.Department

class SchoolAction extends RestfulAction[SchoolBean] {

  override def editSetting(entity: SchoolBean) = {
    val query = OqlBuilder.from(classOf[Institution])
    query.orderBy("name")
    val institutions = entityDao.search(query)
    put("institutions", institutions)
    super.editSetting(entity)
  }
}

class DepartmentAction extends RestfulAction[Department] {

  override def editSetting(entity: Department) = {
    val query = OqlBuilder.from(classOf[Department])
    query.orderBy("name")
    val departments = entityDao.search(query)
    put("departments", departments)
    super.editSetting(entity)
  }
}
