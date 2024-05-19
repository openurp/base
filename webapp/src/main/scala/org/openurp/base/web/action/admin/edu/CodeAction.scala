package org.openurp.base.web.action.admin.edu

import org.beangle.web.action.annotation.action
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.openurp.base.web.action.admin.{AbstractCodeAction, CodeHelper}
import org.openurp.code.edu.model.{AcademicLevel, DegreeLevel, ExamType, TeachingNatureCategory}
import org.openurp.code.sin.model.PressGrade

@action("code/{category}")
class CodeAction extends AbstractCodeAction {

  def editGradeType(): Unit = {
    put("examTypes", entityDao.getAll(classOf[ExamType]))
  }

  def editTeachingNature(): Unit = {
    put("categories", TeachingNatureCategory.values)
  }

  def editPress(): Unit = {
    put("grades", entityDao.getAll(classOf[PressGrade]))
  }

  def editEducationLevel(): Unit = {
    put("levels", entityDao.getAll(classOf[AcademicLevel]))
  }

  def editDegree(): Unit = {
    put("levels", entityDao.getAll(classOf[DegreeLevel]))
  }
}

class CodeIndexAction extends ActionSupport {

  var codeHelper: CodeHelper = _

  def index(): View = {
    val p = Set("org.openurp.code.edu.model", "org.openurp.code.sin.model", "org.openurp.code.trd.model")
    put("metas", codeHelper.getMetas(p))
    forward()
  }

}
