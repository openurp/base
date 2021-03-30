package org.openurp.base.ws.edu

import org.beangle.data.model.Entity
import org.beangle.webmvc.api.annotation.response
import org.openurp.base.edu.model.Classroom
import org.openurp.base.ws.RestfulService

class ClassroomWS extends RestfulService[Classroom] {
  @response
  override def index(): Any = {
    put("properties", List(
      classOf[Classroom] -> List("id", "name", "code", "campus", "building", "roomType", "capacity", "courseCapacity", "examCapacity"),
      classOf[Entity[_]] -> List("id","name")))

    val builder = getQueryBuilder
    builder.orderBy("classroom.name")
    getInt("page") match {
      case Some(p) => entityDao.search(builder)
      case None => entityDao.search(builder.limit(null))
    }
  }
}
