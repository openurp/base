package org.openurp.base.web.action.info.profile

import org.beangle.data.dao.EntityDao
import org.beangle.web.action.annotation.{mapping, param}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.openurp.base.profile.model.StaffProfile

class StaffAction extends ActionSupport {

  var entityDao: EntityDao = _

  @mapping("{id}")
  def index(@param("id") id: String): View = {
    val profile = entityDao.findBy(classOf[StaffProfile], "staff.id", id.toLong)
    put("profile", profile.headOption)
    forward()
  }
}
