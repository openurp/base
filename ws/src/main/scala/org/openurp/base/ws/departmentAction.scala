package org.openurp.base.ws

import org.openurp.base.Department
import org.openurp.base.School
import org.beangle.webmvc.entity.action.RestfulService
import org.beangle.webmvc.api.annotation.response

class SchoolAction extends RestfulService[School]

class DepartmentAction extends RestfulService[Department] {

  @response
  def teaching(): Seq[Department] = {
    entityDao.search(getQueryBuilder().where("department.teaching=true"))
  }
}