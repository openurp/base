package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.Building
import org.openurp.base.Campus
import org.openurp.base.Department
import org.openurp.base.Room

class CampusAction extends RestfulAction[Campus]

class BuildingAction extends RestfulAction[Building] {
    
  override def editSetting(entity: Building) = {
    val query = OqlBuilder.from(classOf[Campus])
    query.orderBy("name")
    val campuses = entityDao.search(query)
    put("campuses", campuses)
    super.editSetting(entity)
  }
}

class RoomAction extends RestfulAction[Room]{
  
  override def editSetting(entity: Room) = {
    val campuses = findItems(classOf[Campus])
    put("campuses", campuses)
    val buildings = findItems(classOf[Building])
    put("buildings", buildings)
    val departments = findItems(classOf[Department])
    put("departments", departments)
    super.editSetting(entity)
  }

  private def findItems[T<:Entity[_]](clazz:Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz)
    query.orderBy("name")
    val items = entityDao.search(query)
    items
  }
}