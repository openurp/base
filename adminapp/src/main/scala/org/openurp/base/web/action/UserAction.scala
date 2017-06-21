package org.openurp.base.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.User
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.commons.collection.Order
import org.beangle.webmvc.api.annotation.ignore
import org.beangle.webmvc.api.view.View
import org.openurp.code.asset.model.RoomType
import org.openurp.base.code.model.UserCategory

@action("{school}/user")
class UserAction extends RestfulAction[User] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[User] = {
    val builder: OqlBuilder[User] = OqlBuilder.from(entityName, "user")
    builder.where("user.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: User): Unit = {
    put("userCategories", entityDao.getAll(classOf[UserCategory]))
    put("departments", getDepartments)

  }

  override protected def indexSetting(): Unit = {
    put("userCategories", entityDao.getAll(classOf[UserCategory]))
    put("departments", getDepartments)
  }

  @ignore
  override protected def saveAndRedirect(entity: User): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }
}
