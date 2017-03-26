package org.openurp.base.web.action

import org.openurp.base.model.{ Department, School }
import org.beangle.webmvc.entity.action.RestfulAction
import org.beangle.commons.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order
import org.openurp.base.model.Building
import org.beangle.webmvc.api.annotation.ignore
import org.beangle.webmvc.api.view.View

class SchoolAction extends RestfulAction[School]

@action("{school}/department")
class DepartmentAction extends RestfulAction[Department] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[Department] = {
    val builder: OqlBuilder[Department] = OqlBuilder.from(entityName, "department")
    builder.where("department.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  @ignore
  override protected def saveAndRedirect(entity: Department): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }

}