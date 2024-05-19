/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
