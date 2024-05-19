package org.openurp.base.web.action.admin.hr

import org.beangle.web.action.annotation.action
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.openurp.base.web.action.admin.{AbstractCodeAction, CodeHelper}
import org.openurp.code.job.model.ProfessionalGrade

@action("code/{category}")
class CodeAction extends AbstractCodeAction {

  def editProfessionalTitle(): Unit = {
    put("grades", entityDao.getAll(classOf[ProfessionalGrade]))
  }
}

class CodeIndexAction extends ActionSupport {

  var codeHelper: CodeHelper = _

  def index(): View = {
    val p = Set("org.openurp.code.hr.model", "org.openurp.code.job.model")
    put("metas", codeHelper.getMetas(p))
    forward()
  }

}
