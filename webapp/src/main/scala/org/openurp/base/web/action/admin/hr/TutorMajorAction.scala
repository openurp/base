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

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.openurp.base.edu.model.{Direction, Major}
import org.openurp.base.hr.model.TutorMajor
import org.openurp.base.model.{Department, Project}
import org.openurp.base.web.helper.TutorMajorImportListener
import org.openurp.code.edu.model.{EducationLevel, EducationType}
import org.openurp.code.job.model.{ProfessionalTitle, TutorType}
import org.openurp.starter.web.support.ProjectSupport

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class TutorMajorAction extends RestfulAction[TutorMajor], ProjectSupport, ImportSupport[TutorMajor], ExportSupport[TutorMajor] {

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("majors", findInProject(classOf[Major]))
    put("tutorTypes", codeService.get(classOf[TutorType]))
    put("departments", findInSchool(classOf[Department]))
    put("titles", codeService.get(classOf[ProfessionalTitle]))
    super.indexSetting()
  }

  override protected def editSetting(tm: TutorMajor): Unit = {
    given project: Project = getProject

    put("majors", findInProject(classOf[Major]))

    val q = OqlBuilder.from(classOf[Direction], "d")
    q.where("d.project=:project", getProject)
    if (tm.persisted) {
      if (null != tm.major && tm.major.persisted) q.where("d.major=:major", tm.major)
    }
    q.where("d.endOn is null")
    put("directions", entityDao.search(q))
    put("project", project)
    super.editSetting(tm)
  }

  override protected def saveAndRedirect(tm: TutorMajor): View = {
    val directionIds = getAll("direction.id", classOf[Long])
    val newDirections = entityDao.find(classOf[Direction], directionIds)
    tm.directions.clear()
    tm.directions.addAll(newDirections)
    super.saveAndRedirect(tm)
  }

  override protected def getQueryBuilder: OqlBuilder[TutorMajor] = {
    val query = super.getQueryBuilder
    val directionName = get("direction.name", "")
    if (Strings.isNotBlank(directionName)) {
      query.where("exists(from tutorMajor.directions d where d.name like :directionName)", "%" + directionName + "%")
    }
    query
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val levels = codeService.get(classOf[EducationLevel]).map(x => x.code + " " + x.name)
    val eduTypes = codeService.get(classOf[EducationType]).map(x => x.code + " " + x.name)
    val majors = findInProject(classOf[Major]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("导师研究领域信息模板")
    sheet.add("导师工号", "tutorMajor.staff.code").length(20).required().remark("≤20位")
    sheet.add("培养层次", "tutorMajor.level.code").ref(levels).required()
    sheet.add("培养类型", "tutorMajor.eduType.code").ref(eduTypes).required()
    sheet.add("专业", "tutorMajor.major.code").ref(majors).required()
    sheet.add("研究方向", "directionNames").required()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "导师研究领域.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TutorMajorImportListener(entityDao, getProject))
  }
}
