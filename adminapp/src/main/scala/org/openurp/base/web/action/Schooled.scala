package org.openurp.base.web.action

import org.openurp.base.model.School
import org.beangle.webmvc.entity.action.RestfulAction
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.model.Department

trait Schooled { this: RestfulAction[_] =>

  def getSchool(): School = {
    val schools = entityDao.findBy(classOf[School], "code", List(get("school").get))
    schools.head
  }

  def getSchoolCode: String = {
    get("school").get
  }

  def getDepartments(): Seq[Department] = {
    entityDao.search(OqlBuilder.from(classOf[Department], "c").where("c.school.code=:schoolCode", getSchoolCode))
  }
}