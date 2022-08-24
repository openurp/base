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

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{Operation, OqlBuilder}
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.web.action.annotation.response
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.execution.MappingHandler
import org.openurp.base.model.*
import org.openurp.base.std.model.Mentor
import org.openurp.base.web.action.admin.ProjectRestfulAction
import org.openurp.base.web.helper.{QueryHelper, URPUserCategory, UrpUserHelper}
import org.openurp.code.edu.model.{Degree, DegreeLevel, EducationDegree}
import org.openurp.code.hr.model.{UserCategory, WorkStatus}
import org.openurp.code.job.model.ProfessionalTitle
import org.openurp.code.person.model.{Gender, IdType}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.Instant

class MentorAction extends ProjectRestfulAction[Mentor] {

  var urpUserHelper: Option[UrpUserHelper] = None

  override def getQueryBuilder: OqlBuilder[Mentor] = {
    QueryHelper.addTemporalOn(super.getQueryBuilder, getBoolean("active"))
  }

  override def editSetting(entity: Mentor) = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
    put("genders", codeService.get(classOf[Gender]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(mentor: Mentor): View = {
    val p = getProject
    var userCode: String = mentor.code
    if (mentor.persisted) {
      val existQuery = OqlBuilder.from[String](classOf[Mentor].getName, "t").select("t.code")
      existQuery.where("t.id=:teacherId", mentor.id)
      entityDao.search(existQuery).headOption foreach { code =>
        userCode = code
      }
    }

    val school = p.school
    val userQuery = OqlBuilder.from(classOf[User], "user").where("user.code=:code", userCode).where("user.school =:school", school)
    val users = entityDao.search(userQuery)
    val user =
      if (users.size == 1) {
        users.head
      } else {
        val u = new User
        u.school = school
        u.category = UserCategory(URPUserCategory.Manager)
        u
      }
    user.beginOn = mentor.beginOn
    user.endOn = mentor.endOn
    user.updatedAt = Instant.now
    user.gender = mentor.gender
    user.department = mentor.department
    user.code = mentor.code
    user.name = mentor.name

    entityDao.saveOrUpdate(  mentor)
    redirect("search", "info.save.success")
  }

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", findInSchool(classOf[Department]))
  }

  override protected def removeAndRedirect(entities: Seq[Mentor]): View = {
    val project = getProject
    val query = OqlBuilder.from(classOf[User], "u")
    query.where("u.code in(:codes) and u.school = :school", entities.map(_.code), project.school)
    val users = entityDao.search(query)
    entityDao.execute(Operation.remove(users).remove(entities))
    redirect("search", "info.remove.success")
  }

  @response
  def downloadTemplate(): Any = {
    given project: Project = getProject

    val genders = getCodes(classOf[Gender]).map(x => x.code + " " + x.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.code")).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("辅导员信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("辅导员工号", "teacher.code").length(10).required().remark("≤10位")
    sheet.add("辅导员姓名", "teacher.name").length(100).required()
    sheet.add("性别", "teacher.gender.code").ref(genders).required()
    sheet.add("所在院系", "teacher.department.code").ref(departs).required()
    sheet.add("任教起始日期", "teacher.beginOn").date().required()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "辅导员信息.xlsx")
  }
}
