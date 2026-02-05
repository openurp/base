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

package org.openurp.base.web.action.admin.std

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.transfer.importer.ImportSetting
import org.beangle.transfer.importer.listener.ForeignerListener
import org.beangle.ems.app.Ems
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport}
import org.beangle.webmvc.view.{Stream, View}
import org.openurp.base.edu.model.{Major, MajorDirection}
import org.openurp.base.model.{Campus, Department, Project}
import org.openurp.base.std.model.{Grade, Squad, Student}
import org.openurp.base.std.service.SquadService
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.SquadImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.LocalDate

class SquadAction extends ProjectRestfulAction[Squad], ExportSupport[Squad], ImportSupport[Squad] {

  var squadService: SquadService = _

  protected override def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
    put("campuses", findInSchool(classOf[Campus]))
  }

  override def getQueryBuilder: OqlBuilder[Squad] = {
    val query = super.getQueryBuilder
    getBoolean("active") foreach { active =>
      val activeCon = s":now between ${query.alias}.beginOn and ${query.alias}.endOn"
      if (active) query.where(activeCon, LocalDate.now())
      else query.where(s"not(${activeCon})", LocalDate.now())
    }
    get("staff.name") foreach { name =>
      if (Strings.isNotEmpty(name)) {
        val nameParam = "%" + name + "%"
        query.where("exists(from squad.mentor m where m.name like :name)" +
          "or exists(from squad.master m where m.name like :name)", nameParam)
      }
    }
    query
  }

  override def editSetting(squad: Squad): Unit = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
    put("campuses", findInSchool(classOf[Campus]))

    val majors = findInProject(classOf[Major])
    put("majors", majors)
    val directions = findInProject(classOf[MajorDirection])
    put("directions", directions)

    super.editSetting(squad)
    put("project", project)
    put("urp", Ems)
  }

  /**
   * 自动指定学生
   *
   * @return
   */
  def autoAssign(): View = {
    val squads = entityDao.find(classOf[Squad], getLongIds("squad"))
    squads foreach { squad =>
      val query = OqlBuilder.from(classOf[Student], "std")
      query.where("std.state.squad is null")
      query.where("std.level=:level", squad.level)
      query.where("std.state.grade=:grade", squad.grade)
      query.where("std.state.department=:department", squad.department)
      squad.stdType foreach { stdType =>
        query.where("std.stdType=:stdType", stdType)
      }
      squad.major foreach { major =>
        query.where("std.state.major=:major", major)
      }
      squad.direction foreach { direction =>
        query.where("std.state.direction=:direction", direction)
      }
      val stds = entityDao.search(query)
      stds foreach { std =>
        std.state.get.squad = Some(squad)
      }
      squad.stdCount = stds.size
      entityDao.saveOrUpdate(stds)
      entityDao.saveOrUpdate(squad)
    }
    redirect("search", "info.save.success")
  }

  /** 手工指派学生
   *
   * @return
   */
  def assign(): View = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    put("students", squadService.getStudents(squad))
    put("squad", squad)
    forward()
  }

  def removeStudent(): View = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    val std = entityDao.get(classOf[Student], getLongId("student"))
    if (std.state.get.squad.contains(squad)) {
      std.state.get.squad = None
      squad.stdCount -= 1
    }
    entityDao.saveOrUpdate(squad, std)
    redirect("assign", s"&squad.id=${squad.id}", "info.remove.success")
  }

  def addStudent(): View = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    val codes = Strings.split(get("codes", ""))
    val stds = entityDao.findBy(classOf[Student], "project" -> squad.project, "code" -> codes)
    stds foreach { std =>
      if (std.state.get.squad.isEmpty) {
        std.state.get.squad = Some(squad)
        squad.stdCount += 1
      }
    }
    entityDao.saveOrUpdate(squad, stds)
    redirect("assign", s"&squad.id=${squad.id}", "info.save.success")
  }

  def saveAssign(): View = {
    forward()
  }

  /**
   * 查看班级信息
   *
   * @return @
   */
  @mapping(value = "{id}")
  override def info(@param("id") id: String): View = {
    val squad = entityDao.get(classOf[Squad], id.toLong)
    put("students", squadService.getStudents(squad))
    put("squad", squad)
    forward()
  }

  @response
  def downloadTemplate(): Any = {
    val project = getProject
    val departs = project.departments.map(x => x.code + " " + x.name).toSeq.sorted
    val grades = entityDao.search(OqlBuilder.from(classOf[Grade], "m").where("m.project=:project", project).orderBy("m.code")).map(x => x.name)
    val majors = entityDao.search(OqlBuilder.from(classOf[Major], "m").where("m.project=:project", project).orderBy("m.code")).map(x => x.code + " " + x.name)
    val directions = entityDao.search(OqlBuilder.from(classOf[MajorDirection], "d").where("d.project=:project", project).orderBy("d.code")).map(x => x.code + " " + x.name)
    val campuses = project.campuses.toSeq.map(x => x.code + " " + x.name).toSeq.sorted
    val levels = project.levels.map(x => x.code + " " + x.name).toSeq.sorted
    val stdTypes = project.stdTypes.map(x => x.code + " " + x.name).toSeq.sorted
    val endTypes = project.eduTypes.map(x => x.code + " " + x.name).toSeq.sorted

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("班级信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("班级代码", "squad.code").length(15).required().remark("≤15位")
    sheet.add("班级名称", "squad.name").length(100).required()
    sheet.add("班级英文名称", "squad.enName").length(300)
    sheet.add("培养层次代码", "squad.level.code").ref(levels).required()
    sheet.add("培养类型代码", "squad.eduType.code").ref(endTypes).required()
    sheet.add("学生类别代码", "squad.stdType.code").ref(stdTypes).required()
    sheet.add("年级", "grade.name").ref(grades).required()
    sheet.add("院系代码", "squad.department.code").ref(departs).required()
    sheet.add("专业代码", "squad.major.code").ref(majors)
    sheet.add("专业方向代码", "squad.direction.code").ref(directions)
    sheet.add("计划人数", "squad.planCount").required().decimal()
    sheet.add("辅导员工号", "squad.mentor.code")
    sheet.add("班主任工号", "squad.master.code")
    sheet.add("校区代码", "squad.campus.code").ref(campuses)
    sheet.add("生效日期", "squad.beginOn").date().required()
    sheet.add("失效日期", "squad.endOn").date().required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.xlsx, "班级模板.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fk = new ForeignerListener(entityDao)
    fk.addForeigerKey("code")
    setting.listeners = List(fk, new SquadImportListener(getProject, entityDao))
  }

  /** 统计班级人数
   *
   * @return
   */
  def statStdCount(): View = {
    val squadIds = getLongIds("squad")
    val squads = entityDao.find(classOf[Squad], squadIds)
    val updated = squadService.statStdCount(squads)
    redirect("search", if (updated == 0) "班级人数无变化" else s"更新了${updated}个班级的人数")
  }

  def copy(): View = {
    val squad = entityDao.get(classOf[Squad], getLongId("squad"))
    val copy = new Squad()
    copy.project = squad.project
    copy.code = squad.code + "(copy)"
    copy.name = squad.name + "(复制)"
    copy.enName = copy.enName
    copy.shortName = squad.shortName

    copy.grade = squad.grade
    copy.level = squad.level
    copy.eduType = squad.eduType
    copy.stdType = squad.stdType
    copy.department = squad.department
    copy.major = squad.major
    copy.direction = squad.direction
    copy.beginOn = squad.beginOn
    copy.endOn = squad.endOn
    copy.campus = squad.campus
    copy.planCount = squad.planCount
    copy.stdCount = squad.stdCount

    copy.mentor = squad.mentor
    copy.master = squad.master
    editSetting(copy)
    put("squad", copy)
    forward("form")
  }

}
