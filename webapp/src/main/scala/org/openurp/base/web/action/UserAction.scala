package org.openurp.base.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.User
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order

@action("{school}/user")
class UserAction extends RestfulAction[User] {
  override protected def getQueryBuilder(): OqlBuilder[User] = {
    val builder: OqlBuilder[User] = OqlBuilder.from(entityName, "user")
    builder.where("user.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
