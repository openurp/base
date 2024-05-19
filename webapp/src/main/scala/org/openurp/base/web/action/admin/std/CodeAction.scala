package org.openurp.base.web.action.admin.std

import org.beangle.web.action.annotation.action
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.openurp.base.web.action.admin.{AbstractCodeAction, CodeHelper}
import org.openurp.code.std.model.StdLabelType

@action("code/{category}")
class CodeAction extends AbstractCodeAction {

  def editStdLabel(): Unit = {
    put("labelTypes", entityDao.getAll(classOf[StdLabelType]))
  }
}

class CodeIndexAction extends ActionSupport {

  var codeHelper: CodeHelper = _

  def index(): View = {
    val p = Set("org.openurp.code.std.model")
    put("metas", codeHelper.getMetas(p))
    forward()
  }

}
