package org.openurp.teach.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{ Department, Teacher, TeacherJournal }
import org.openurp.base.code.{ Degree, Education, TeacherTitle, TutorType }

class TeacherJournalAction extends RestfulAction[TeacherJournal] {
  override def editSetting(entity: TeacherJournal) = {
    val departments = findItems(classOf[Department])
    put("departments", departments)

    val teachers = findItems(classOf[Teacher])
    put("teachers", teachers)

    val titles = findItems(classOf[TeacherTitle])
    put("titles", titles)

    val educations = findItems(classOf[Education])
    put("educations", educations)

    val degrees = findItems(classOf[Degree])
    put("degrees", degrees)

    val tutorTypes = findItems(classOf[TutorType])
    put("tutorTypes", tutorTypes)

    super.editSetting(entity)
  }

  private def findItems[T <: Entity[_]](clazz: Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz)
    query.orderBy("name")
    val items = entityDao.search(query)
    items
  }

}


