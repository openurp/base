package org.openurp.base.web.action

import java.net.URL

import org.beangle.commons.io.IOs
import org.beangle.data.dao.{ EntityDao, OqlBuilder }
import org.beangle.security.context.SecurityContext
import org.beangle.security.mgt.SecurityManager
import org.beangle.security.realm.cas.CasConfig
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.{ ignore, mapping }
import org.beangle.webmvc.api.view.View
import org.openurp.base.model.{ School, User }
import org.openurp.platform.api.app.UrpApp
import org.openurp.platform.api.security.Securities

class IndexAction extends ActionSupport {
  var entityDao: EntityDao = _
  var casConfig: CasConfig = _
  var securityManager: SecurityManager = _

  @mapping("{school}")
  def school(): String = {
    val menuJson = IOs.readString(new URL("http://platform.urp.sfu.edu.cn/security/func/" + UrpApp.name + "/menus/user/" + Securities.user + ".json").openStream())
    put("menuJson", menuJson)

    val appJson = IOs.readString(new URL("http://platform.urp.sfu.edu.cn/user/apps/" + Securities.user + ".json").openStream())
    put("appJson", appJson)

    entityDao.getAll(classOf[School]) foreach { school => put("school", school) }

    put("user", getUser())
    put("casConfig", casConfig)
    put("webappBase", "http://webapp.urp.sfu.edu.cn")
    put("thisAppName", UrpApp.name)

    forward()
  }

  def index(): View = {
    val builder = OqlBuilder.from(classOf[School], "s").orderBy("s.beginOn desc").cacheable()
    val schools = entityDao.search(builder)
    if (schools.isEmpty) throw new RuntimeException("Cannot find any valid schools")

    redirect("school", "&school=" + schools.head.code, null)
  }

  def logout(): View = {
    securityManager.logout(SecurityContext.session)
    redirect(to(casConfig.casServer + "/logout"), null)
  }

  def getUser(): User = {
    val builder = OqlBuilder.from(classOf[User], "user")
    builder.where("user.code=:code", Securities.user) //.where("user.school.code=:schoolCode", get("school").get)
    val users = entityDao.search(builder)
    if (users.isEmpty) {
      throw new RuntimeException("Cannot find staff with code " + Securities.user)
    } else {
      users.head
    }
  }
}