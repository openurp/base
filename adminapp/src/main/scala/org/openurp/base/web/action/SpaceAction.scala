package org.openurp.base.web.action

import org.beangle.commons.collection.Order
import org.beangle.commons.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.action
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Building
import org.openurp.base.model.Campus
import org.openurp.base.model.Room
import org.openurp.code.asset.model.RoomType
import org.openurp.base.model.Department
import org.beangle.webmvc.api.annotation.ignore
import org.beangle.commons.model.Updated
import org.beangle.webmvc.execution.Handler
import org.beangle.webmvc.api.view.View
import org.openurp.base.model.School

@action("{school}/campus")
class CampusAction extends RestfulAction[Campus] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[Campus] = {
    val builder: OqlBuilder[Campus] = OqlBuilder.from(entityName, "campus")
    builder.where("campus.school.code=:schoolCode", get("school").get)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }
  @ignore
  override protected def saveAndRedirect(entity: Campus): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }
}

@action("{school}/building")
class BuildingAction extends RestfulAction[Building] with Schooled {
  override protected def getQueryBuilder(): OqlBuilder[Building] = {
    val builder: OqlBuilder[Building] = OqlBuilder.from(entityName, "building")
    builder.where("building.school.code=:schoolCode", getSchoolCode)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: Building): Unit = {
    put("campuses", getCampuses)
  }

  override protected def indexSetting(): Unit = {
    put("campuses", getCampuses)
  }

  private def getCampuses(): Seq[Campus] = {
    entityDao.search(OqlBuilder.from(classOf[Campus], "c").where("c.school.code=:schoolCode", getSchoolCode))
  }

  @ignore
  override protected def saveAndRedirect(entity: Building): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }
}

@action("{school}/room")
class RoomAction extends RestfulAction[Room] with Schooled {

  override protected def editSetting(entity: Room): Unit = {
    put("roomTypes", entityDao.getAll(classOf[RoomType]))
    put("campuses", getCampuses)
    put("departments", getDepartments)
    put("buildings", getBuildings)
  }

  override protected def indexSetting(): Unit = {
    put("roomTypes", entityDao.getAll(classOf[RoomType]))
    put("campuses", getCampuses)
    put("buildings", getBuildings)
  }

  private def getCampuses(): Seq[Campus] = {
    entityDao.search(OqlBuilder.from(classOf[Campus], "c").where("c.school.code=:schoolCode", getSchoolCode))
  }

  private def getBuildings(): Seq[Building] = {
    entityDao.search(OqlBuilder.from(classOf[Building], "c").where("c.school.code=:schoolCode", getSchoolCode))
  }

  override protected def getQueryBuilder(): OqlBuilder[Room] = {
    val builder: OqlBuilder[Room] = OqlBuilder.from(entityName, "room")
    builder.where("room.school.code=:schoolCode", getSchoolCode)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  @ignore
  override protected def saveAndRedirect(entity: Room): View = {
    entity.school = getSchool
    super.saveAndRedirect(entity)
  }

}

