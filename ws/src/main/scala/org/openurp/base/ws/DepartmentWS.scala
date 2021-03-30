package org.openurp.base.ws

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.response
import org.openurp.base.model.{Department, School}

class SchoolWS extends RestfulService[School]

class DepartmentWS extends RestfulService[Department] {

  @response
  override def index(): Any = {
    put("properties", List(classOf[Department] -> List("id", "code", "name", "shortName")))
    getInt("page") match {
      case Some(p) => entityDao.search(getQueryBuilder)
      case None => entityDao.search(getQueryBuilder.limit(null))
    }
  }

  @response
  def teaching(): Seq[Department] = {
    put("properties", List(classOf[Department] -> List("id", "code", "name", "shortName")))
    entityDao.search(getQueryBuilder.limit(null).where("department.teaching=true"))
  }

  override protected def getQueryBuilder: OqlBuilder[Department] = {
    super.getQueryBuilder.orderBy("department.code")
  }
}
