/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.admin.edu

import org.beangle.commons.codec.digest.Digests
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.ClassLoaders
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.transfer.excel.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.ems.app.Ems
import org.beangle.webmvc.api.annotation.{mapping, param, response}
import org.beangle.webmvc.api.view.{Stream, View}
import org.openurp.base.edu.code.model.StdType
import org.openurp.base.edu.model._
import org.openurp.base.edu.web.helper.{QueryHelper, SquadImportListener}
import org.openurp.base.model.{Campus, Department}
import org.openurp.code.edu.model.EducationLevel

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class SquadAction extends ProjectRestfulAction[Squad] {

  protected override def indexSetting(): Unit = {
    put("levels", getCodes(classOf[EducationLevel]))
    put("departments", findInSchool(classOf[Department]))
    put("campuses", findInSchool(classOf[Campus]))
  }

  override def getQueryBuilder: OqlBuilder[Squad] = {
    QueryHelper.addTemporalOn(super.getQueryBuilder, getBoolean("active"))
  }

  override def editSetting(entity: Squad) = {
    put("levels", getCodes(classOf[EducationLevel]))
    put("departments", findInSchool(classOf[Department]))
    put("campuses", findInSchool(classOf[Campus]))

    val majors = findInProject(classOf[Major])
    put("majors", majors)
    val directions = findInProject(classOf[Direction])
    put("directions", directions)
    val stdTypes = getCodes(classOf[StdType])
    put("stdTypes", stdTypes)

    super.editSetting(entity)
    put("project", getProject)
    put("urp", Ems)
  }

  /**
   * 查看班级信息
   *
   * @return @
   */
  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val builder = OqlBuilder.from(classOf[StudentState], "studentState")
    builder.where("studentState.squad.id=:id", id.toLong)
    val studentStates = entityDao.search(builder)
    val students = Collections.newBuffer[Student]
    studentStates.foreach { studentState => students += studentState.std }
    val status = Collections.newMap[String, StudentState]
    studentStates.foreach { studentState => status.put(studentState.std.user.code, studentState) }
    put("students", students)
    put("status", status)
    put("urp", Ems)
    put("md5", Md5)
    super.info(id)
  }

  object Md5 {
    def digest(a: String): String = {
      Digests.md5Hex(a)
    }
  }

  /**
   * 下载模板
   */
  def downloadSquadStdTemp: View = {
    Stream(ClassLoaders.getResourceAsStream("template/squad.xls").get, "application/vnd.ms-excel", "班级信息.xls")
  }


  @response
  def downloadTemplate(): Any = {
    val project = getProject
    val departs = project.departments.map(x => x.code + " " + x.name)
    val majors = entityDao.search(OqlBuilder.from(classOf[Major], "m").where("m.project=:project", project).orderBy("m.code")).map(x => x.code + " " + x.name)
    val directions = entityDao.search(OqlBuilder.from(classOf[Direction], "d").where("d.project=:project", project).orderBy("d.code")).map(x => x.code + " " + x.name)
    val campuses = project.campuses.toSeq.map(x => x.code + " " + x.name)
    val levels = project.levels.map(x => x.code + " " + x.name)
    val stdTypes = project.stdTypes.map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("班级信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("班级代码", "squad.code").length(10).required().remark("≤10位")
    sheet.add("班级名称", "squad.name").length(100).required()
    sheet.add("班级英文名称", "squad.enName").length(300)
    sheet.add("培养层次代码", "squad.level.code").ref(levels).required()
    sheet.add("学生类别代码", "squad.stdType.code").ref(stdTypes).required()
    sheet.add("年级", "squad.grade").required()
    sheet.add("院系代码", "squad.department.code").ref(departs).required()
    sheet.add("专业代码", "squad.major.code").ref(majors).required()
    sheet.add("专业方向代码", "squad.direction.code").ref(directions)
    sheet.add("计划人数", "squad.planCount").required().decimal()
    sheet.add("辅导员工号", "squad.instructor.code")
    sheet.add("校区代码", "squad.campus.code").ref(campuses).required()
    sheet.add("生效日期", "squad.beginOn").date().required()
    sheet.add("失效日期", "squad.endOn").date().required()

    val code = schema.createScheet("数据字典")
    code.add("培养层次代码").data(levels)
    code.add("学生类别代码").data(stdTypes)
    code.add("院系代码").data(departs)
    code.add("专业代码").data(majors)
    code.add("专业方向代码").data(directions)
    code.add("校区代码").data(campuses)

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "班级模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fk = new ForeignerListener(entityDao)
    fk.addForeigerKey("code")
    setting.listeners = List(fk, new SquadImportListener(getProject, entityDao))
  }

}
