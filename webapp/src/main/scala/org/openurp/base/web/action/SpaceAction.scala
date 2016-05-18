package org.openurp.base.web.action

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Building
import org.openurp.base.model.Campus
import org.openurp.base.model.Room
import org.openurp.code.asset.model.RoomType

@action("{school}/campus")
class CampusAction extends RestfulAction[Campus] {
  override protected def getQueryBuilder(): OqlBuilder[Campus] = {
    val builder: OqlBuilder[Campus] = OqlBuilder.from(entityName, "campus")
    builder.where("campus.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
@action("{school}/building")
class BuildingAction extends RestfulAction[Building] {
  override protected def getQueryBuilder(): OqlBuilder[Building] = {
    val builder: OqlBuilder[Building] = OqlBuilder.from(entityName, "building")
    builder.where("building.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}

@action("{school}/room")
class RoomAction extends RestfulAction[Room] {
  
  override protected def editSetting(entity: Room): Unit = {
    put("roomTypes",entityDao.getAll(classOf[RoomType]));
  }
  
  override protected def getQueryBuilder(): OqlBuilder[Room] = {
    val builder: OqlBuilder[Room] = OqlBuilder.from(entityName, "room")
    builder.where("room.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
