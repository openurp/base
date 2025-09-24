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

import org.beangle.commons.bean.Initializing
import org.beangle.commons.bean.orderings.CollatorOrdering
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.reflect.BeanInfos
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.stat.{Columns, Matrix}
import org.beangle.webmvc.support.ActionSupport
import org.beangle.webmvc.support.helper.QueryHelper
import org.beangle.webmvc.view.View
import org.openurp.base.edu.model.Major
import org.openurp.base.hr.model.{Staff, Teacher, TutorMajor}
import org.openurp.base.model.Department
import org.openurp.base.std.model.{Grade, Student, Tutorship}
import org.openurp.code.edu.model.{DegreeLevel, EducationLevel}
import org.openurp.code.job.model.ProfessionalGrade
import org.openurp.starter.web.support.ProjectSupport

import java.time.LocalDate

class TutorStatAction extends ActionSupport, ProjectSupport, Initializing {

  var entityDao: EntityDao = _

  override def init(): Unit = {
    if (!BeanInfos.cached(classOf[Matrix])) {
      BeanInfos.of(classOf[Matrix])
    }
  }

  /** 按照层次统计，各个方向下都有哪些导师
   *
   * @return
   */
  def direction(): View = {
    val project = getProject
    val tms = entityDao.findBy(classOf[TutorMajor], "major.project", project)
    val datas = tms.flatMap { tm =>
      tm.directions.map(direction => (tm.level, tm.major, direction, tm.staff))
    }
    val levelDirectionTutors = datas.groupBy(_._1).map { d =>
      (d._1, d._2.groupBy(_._3).map(x => (x._1, x._2.map(_._4).distinct)))
    }
    put("levels", levelDirectionTutors.keys)
    put("levelDirectionTutors", levelDirectionTutors)
    forward()
  }

  /** 按照专业学科统计
   *
   * @return
   */
  def major(): View = {
    val school = getProject.school
    val q = OqlBuilder.from[Array[Any]](classOf[Staff].getName + " staff," + classOf[TutorMajor].getName + " tm")
    q.where("staff.tutorType is not null")
    q.where("staff.school=:school", school)
    q.where(s"tm.staff=staff")
    q.select(s"staff.title.grade.id,staff.degreeLevel.id,max(tm.level.id)," + //同一个专业的最高培养层次
      s"staff.degreeAwardBy,staff.parttime," +
      s"tm.major.id,staff.birthday,staff.id")
    q.groupBy(s"staff.title.grade.id,staff.degreeLevel.id," +
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

    val ds = Columns(entityDao)
    ds.add("grade", "职称等级", datas, classOf[ProfessionalGrade])
    ds.add("degreeLevel", "学位水平", datas, classOf[DegreeLevel])
    ds.add("eduLevel", "导师类型", datas, classOf[EducationLevel])
    ds.add("degreeAwardOutside", "授予学位", datas, Map(0 -> "本校", 1 -> "外校"))
    ds.add("parttime", "兼职", datas, Map(false -> "非兼职", true -> "兼职"))
    ds.add("major", "学科专业", datas, classOf[Major])
    ds.add("age", "兼职", datas, Map("0" -> "未知", "1-25" -> "25岁及以下", "26-35" -> "26至35岁", "36-45" -> "36至45岁", "46-59" -> "46至59岁", "60-150" -> "60岁及以上"))
    val matrix = new Matrix(ds.build(), datas)
    put("majors", matrix.getColumn("major").values)
    put("matrixes", matrix.split("major"))
    forward()
  }

  /** 按照带学生统计
   *
   * @return
   */
  def std(): View = {
    val q = OqlBuilder.from[Array[Any]](classOf[Student].getName, "std")
    q.join("std.tutors", "st")
    q.where("st.tutorship=:ship", Tutorship.Major)
    //在籍学生，无论是否在校
    q.where("std.registed = true and :today between std.beginOn and std.endOn", LocalDate.now)
    q.select("st.tutor.id,st.tutor.department.id,std.level.id,std.state.grade.id,std.graduationDeferred,count(*)")
    q.groupBy("st.tutor.id,st.tutor.department.id,std.level.id,std.state.grade.id,std.graduationDeferred")
    val datas = Collections.newBuffer[Matrix.Row]
    val teacherIds = Collections.newSet[Long]
    val departIds = Collections.newSet[Int]
    val gradeIds = Collections.newSet[Long]
    entityDao.search(q) foreach { d =>
      val teacherId = d(0).asInstanceOf[Long] //教师ID
      val departId = d(1).asInstanceOf[Int] //培养层次
      val levelId = d(2).asInstanceOf[Int] //培养层次
      val gradeId = d(3).asInstanceOf[Long] //培养层次
      val deferred = d(4).asInstanceOf[Boolean] //是否延期
      val count = d(5).asInstanceOf[Number].intValue() //人数
      teacherIds.addOne(teacherId)
      departIds.addOne(departId)
      gradeIds.addOne(gradeId)
      val data = Matrix.Row(Seq(teacherId, departId, levelId, gradeId, deferred), Array(count))
      datas.addOne(data)
    }
    val teachers = entityDao.find(classOf[Teacher], teacherIds).sortBy(_.name)(new CollatorOrdering(true))
    put("teachers", teachers)
    put("departTeachers", teachers.groupBy(_.department))
    put("departs", entityDao.find(classOf[Department], departIds).sortBy(_.code))
    val grades = entityDao.find(classOf[Grade], gradeIds).sortBy(_.name)
    put("grades", grades)

    val ds = Columns(entityDao)
    ds.add("tutor", "导师", datas)
    ds.add("depart", "院系", datas, classOf[Department])
    ds.add("level", "培养层次", datas, classOf[EducationLevel])
    ds.add("grade", "年级", datas, classOf[Grade])
    ds.add("graduationDeferred", "是否延期", datas, Map(false -> "非延期", true -> "延期"))
    val matrix = new Matrix(ds.build(), datas)
    put("matrixes", matrix.split("depart"))
    forward()
  }

  def stdList(): View = {
    val q = OqlBuilder.from(classOf[Student], "std")
    q.where("exists(from std.tutors st where st.tutor.id=:tutorId and st.tutorship=:ship)", getLongId("tutor"), Tutorship.Major)
    //在籍学生，无论是否在校
    q.where("std.registed = true and :today between std.beginOn and std.endOn", LocalDate.now)
    QueryHelper.populate(q)
    q.orderBy("std.code desc")
    val stds = entityDao.search(q)
    put("stds", stds)
    forward()
  }
}
