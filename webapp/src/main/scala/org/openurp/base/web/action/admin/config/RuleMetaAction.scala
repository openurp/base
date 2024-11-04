package org.openurp.base.web.action.admin.config

import org.beangle.commons.collection.Collections
import org.beangle.web.action.context.Params
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.support.helper.PopulateHelper
import org.openurp.base.config.model.{Business, RuleMeta, RuleParamMeta}

class RuleMetaAction extends RestfulAction[RuleMeta] {

  override protected def editSetting(entity: RuleMeta): Unit = {
    put("businesses", entityDao.getAll(classOf[Business]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(meta: RuleMeta): View = {
    val params = meta.params.toBuffer.map(x => (x.name, x)).toMap
    val paramNames = Collections.newSet[String]
    (0 to 10) foreach { i =>
      val p = Params.sub(i.toString)
      val paramMeta = PopulateHelper.populate(new RuleParamMeta, p)
      if (null != paramMeta.name && null != paramMeta.title) {
        paramNames += paramMeta.name
        params.get(paramMeta.name) match {
          case Some(x) =>
            x.title = paramMeta.title
            x.description = paramMeta.description
          case None =>
            paramMeta.ruleMeta = meta
            meta.params += paramMeta
        }
      }
    }
    meta.params.subtractAll(meta.params.filter(x => !paramNames.contains(x.name)))
    super.saveAndRedirect(meta)
  }
}
