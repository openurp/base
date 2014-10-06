package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.Institution
import org.openurp.base.domain.DepartmentBean
import org.openurp.base.domain.SchoolBean

class SchoolBeanAction extends RestfulAction[SchoolBean] {

  override def editSetting(entity: SchoolBean) = {
    val query = OqlBuilder.from(classOf[Institution])
    query.orderBy("name")
    val institutions = entityDao.search(query)
    put("institutions", institutions)
    super.editSetting(entity)
  }
}

class DepartmentBeanAction extends RestfulAction[DepartmentBean] {

  override def editSetting(entity: DepartmentBean) = {
    val query = OqlBuilder.from(classOf[DepartmentBean])
    query.orderBy("name")
    val departmentBeans = entityDao.search(query)
    put("departmentBeans", departmentBeans)
    super.editSetting(entity)
  }
}
