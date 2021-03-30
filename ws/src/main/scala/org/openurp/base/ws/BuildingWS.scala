package org.openurp.base.ws

import org.beangle.data.model.Entity
import org.beangle.webmvc.api.annotation.response
import org.openurp.base.model.Building

class BuildingWS extends RestfulService[Building] {
  @response
  override def index(): Any = {
    put("properties", List(
      classOf[Building] -> List("id", "name", "code", "campus", "enName", "shortName"),
      classOf[Entity[_]] -> List("id")))

    val builder = getQueryBuilder
    builder.orderBy("building.code")
    getInt("page") match {
      case Some(p) => entityDao.search(builder)
      case None => entityDao.search(builder.limit(null))
    }
  }
}
