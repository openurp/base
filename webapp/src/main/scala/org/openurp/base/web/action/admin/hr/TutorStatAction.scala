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

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.reflect.BeanInfos
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.Major
import org.openurp.base.hr.model.{Staff, TutorMajor}
import org.openurp.base.web.helper.{Dimensions, Matrix}
import org.openurp.code.edu.model.{DegreeLevel, EducationLevel}
import org.openurp.code.job.model.ProfessionalGrade
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class TutorStatAction extends ActionSupport, ProjectSupport {

  var entityDao: EntityDao = _

  def major(): View = {
    BeanInfos.of(classOf[Matrix])

    val school = getProject.school
    val q = OqlBuilder.from[Array[Any]](classOf[Staff].getName + " staff," + classOf[TutorMajor].getName + " tm")
    q.where("staff.tutorType is not null")
    q.where("staff.school=:school", school)
    q.where(s"tm.staff=staff")
    q.select(s"staff.title.grade.id,staff.degreeLevel.id,max(tm.level.id)," +
      s"staff.degreeAwardBy,staff.parttime," +
      s"tm.major.id,staff.birthday,staff.id")
    q.groupBy(s"staff.title.grade.id,staff.degreeLevel.id,tm.level.id," +
      s"staff.degreeAwardBy,staff.parttime," +
      s"tm.major.id,staff.birthday,staff.id")
    val datas = Collections.newBuffer[Matrix.Row]
    val today = LocalDate.now()
    entityDao.search(q) foreach { d =>
      val gradeId = d(0).asInstanceOf[Int] //职称等级
      val degreeLevelId = d(1).asInstanceOf[Int] //学位水平
      val levelId = d(2).asInstanceOf[Int] //培养层次
      val outside = d(3).asInstanceOf[String] //校外授予学位
      val awardByOutSide = if (null == outside || outside == school.name) 0 else 1
      val parttime = d(4).asInstanceOf[Boolean] //兼职
      val majorId = d(5).asInstanceOf[Long] //专业
      val birthday = d(6).asInstanceOf[LocalDate]
      val age =
        if (null == birthday) "0"
        else
          val years = birthday.until(today).getYears
          if (years <= 0) "0"
          else if (years <= 25) "1-25"
          else if (years <= 35) "26-35"
          else if (years <= 45) "36-45"
          else if (years <= 59) "46-59"
          else "60-150"

      val total = d(7).asInstanceOf[Long] //总数
      val data = Matrix.Row(Seq(gradeId, degreeLevelId, levelId, awardByOutSide, parttime, majorId, age), Array(1))
      datas.addOne(data)
    }

    val ds = Dimensions(entityDao)
    ds.add("grade", "职称等级", datas, classOf[ProfessionalGrade])
    ds.add("degreeLevel", "学位水平", datas, classOf[DegreeLevel])
    ds.add("eduLevel", "导师类型", datas, classOf[EducationLevel])
    ds.add("degreeAwardOutside", "授予学位", datas, Map(0 -> "本校", 1 -> "外校"))
    ds.add("parttime", "兼职", datas, Map(false -> "非兼职", true -> "兼职"))
    ds.add("major", "学科专业", datas, classOf[Major])
    ds.add("age", "兼职", datas, Map("0" -> "未知", "1-25" -> "25岁及以下", "26-35" -> "26至35岁", "36-45" -> "36至45岁", "46-59" -> "46至59岁", "60-150" -> "60岁及以上"))
    val matrix = new Matrix(ds.build(), datas)
    put("majors", matrix.getDimension("major").values)
    put("matrixes", matrix.split("major"))
    forward()
  }
}
