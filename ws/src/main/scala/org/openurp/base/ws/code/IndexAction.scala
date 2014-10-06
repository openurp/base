package org.openurp.base.ws.code

import org.beangle.commons.lang.{ClassLoaders, Strings}
import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.data.model.dao.EntityDao
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.annotation.{action, mapping}
import org.beangle.webmvc.api.util.CacheControl
import org.openurp.code.BaseCode

@action("")
class IndexAction extends ActionSupport {

  var entityDao: EntityDao = _
  
  val seq = Seq("country", "nation", "codeCategory")

  @mapping("{code_meta}")
  def index(meta: String): String = {
    val clazz = ClassLoaders.loadClass("org.openurp.kernel.base.code.domain." + Strings.capitalize(meta)).asInstanceOf[Class[Entity[Integer]]]
    val builder = OqlBuilder.from(clazz, "code")
    if (clazz.isInstanceOf[BaseCode]){
    	builder.orderBy(get("orderBy", "code.code"))
    }else{
    	builder.orderBy(get("orderBy", "code.name"))
    }

    builder.select("new ")
    val res = entityDao.search(builder)
    put("codes", res)

    val dataType = get("dataType", "json")
    val viewName = (if(seq.indexOf(meta) >= 0) meta else "index") + "_" + dataType
    CacheControl.expiresAfter(7)    
    forward(viewName)
  }
}