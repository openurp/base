package org.openurp.teach.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{ Department, Person, Teacher }
import org.openurp.base.code.{ Degree, Education, TeacherState, TeacherTitle, TeacherType, TeacherUnitType, TutorType }

class TeacherAction extends RestfulAction[Teacher] {
  override def editSetting(entity: Teacher) = {
    val departments = findItems(classOf[Department])
    put("departments", departments)

    val persons = findItems(classOf[Person])
    put("persons", persons)

    val titles = findItems(classOf[TeacherTitle])
    put("titles", titles)

    val educations = findItems(classOf[Education])
    put("educations", educations)

    val degrees = findItems(classOf[Degree])
    put("degrees", degrees)

    val tutorTypes = findItems(classOf[TutorType])
    put("tutorTypes", tutorTypes)

    val parttimeDeparts = findItems(classOf[Department])
    put("parttimeDeparts", parttimeDeparts)

    val teacherTypes = findItems(classOf[TeacherType])
    put("teacherTypes", teacherTypes)

    val unitTypes = findItems(classOf[TeacherUnitType])
    put("unitTypes", unitTypes)

    val states = findItems(classOf[TeacherState])
    put("states", states)

    super.editSetting(entity)
  }

  private def findItems[T <: Entity[_]](clazz: Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz)
    query.orderBy("name")
    val items = entityDao.search(query)
    items
  }

}

