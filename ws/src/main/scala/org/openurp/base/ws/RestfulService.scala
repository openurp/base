package org.openurp.base.ws

import org.beangle.data.model.Entity
import org.beangle.webmvc.api.action.{ActionSupport, MimeSupport}
import org.beangle.webmvc.api.annotation.{mapping, param, response}
import org.beangle.webmvc.api.context.Params
import org.beangle.webmvc.entity.action.EntityAction

class RestfulService[T <: Entity[_]] extends ActionSupport with EntityAction[T] with MimeSupport {

  @response
  def index(): Any = {
    getInt("page") match {
      case Some(_) => entityDao.search(getQueryBuilder)
      case None    => entityDao.search(getQueryBuilder.limit(null))
    }
  }

  @response
  @mapping(value = "{id}")
  def info(@param("id") id: String): T = {
    Params.converter.convert(id, entityDao.domain.getEntity(entityName).get.id.clazz) match {
      case None           => null.asInstanceOf[T]
      case Some(entityId) => getModel[T](entityName, entityId)
    }
  }

}
