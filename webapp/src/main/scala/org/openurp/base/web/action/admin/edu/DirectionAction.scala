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

package org.openurp.base.web.action.admin.edu

import org.beangle.commons.activation.MediaTypes
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.action.ImportSupport
import org.beangle.webmvc.support.helper.QueryHelper
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.base.edu.model.{Major, MajorDirection, MajorDirectionJournal}
import org.openurp.base.model.Project
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.DirectionImportListener
import org.openurp.code.edu.model.EducationLevel

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

class DirectionAction extends ProjectRestfulAction[MajorDirection], ImportSupport[MajorDirection] {

  override def indexSetting(): Unit = {
    given project: Project = getProject

    put("departs", getDeparts)
    put("levels", getCodes(classOf[EducationLevel]))
  }

  override def getQueryBuilder: OqlBuilder[MajorDirection] = {
    val query = super.getQueryBuilder
    getInt("department.id") foreach { d =>
      query.where("exists(from direction.journals as dj where dj.depart.id=:departId)", d)
    }
    getInt("level.id") foreach { d =>
      query.where("exists(from direction.journals as dj where dj.level.id=:levelId)", d)
    }
    QueryHelper.addActive(query, getBoolean("active"))
    query
  }

  override def editSetting(direction: MajorDirection) = {
    given project: Project = getProject

    if !direction.persisted then direction.beginOn = LocalDate.now
    val majors = findInProject(classOf[Major])
    put("majors", majors)
    super.editSetting(direction)
  }

  protected override def saveAndRedirect(d: MajorDirection): View = {
    d.project = getProject

    if (!d.persisted) {
      val major = entityDao.get(classOf[Major], d.major.id)
      val departs = major.journals.map(j => j.depart).toSet
      val levels = major.journals.map(j => j.level).toSet
      if (departs.size == 1 && levels.size == 1) {
        val dj = new MajorDirectionJournal
        dj.direction = d
        dj.depart = departs.head
        dj.level = levels.head
        dj.beginOn = LocalDate.now
        d.journals += dj
      }
    }
    saveMore(d)
    entityDao.evict(classOf[MajorDirection])
    redirect("search", "info.save.success")
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val majors = entityDao.findBy(classOf[Major], "project", project).map(x => x.code + " " + x.name)
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("专业方向模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("方向代码", "direction.code").length(10).required().remark("≤10位")
    sheet.add("方向名称", "direction.name").length(100).required()
    sheet.add("方向英文名称", "direction.enName").length(300)
    sheet.add("所属专业", "direction.major.code").ref(majors)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "专业方向模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new DirectionImportListener(entityDao, getProject))
  }

  override protected def simpleEntityName: String = "direction"
}
