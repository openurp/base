package org.openurp.base.ds

import org.openurp.base.Department
import org.openurp.base.School
import org.beangle.webmvc.entity.action.RestfulService
import org.beangle.webmvc.api.annotation.response

class SchoolWS extends RestfulService[School]

class DepartmentWS extends RestfulService[Department] {

  @response
  def teaching(): Seq[Department] = {
    entityDao.search(getQueryBuilder().where("department.teaching=true"))
  }
}