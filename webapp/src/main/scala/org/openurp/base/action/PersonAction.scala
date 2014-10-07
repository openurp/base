package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.{ Country, Gender, IdType, PersonCategory }
import org.openurp.base.domain.{ DepartmentBean, PersonBean }
import org.openurp.base.Person

class PersonAction extends RestfulAction[Person] {

  override def editSetting(entity: Person) = {
    val idTypes = findItems(classOf[IdType])
    put("idTypes", idTypes)
    val genders = findItems(classOf[Gender])
    put("genders", genders)
    val countries = findItems(classOf[Country])
    put("countries", countries)
    val departments = findItems(classOf[DepartmentBean])
    put("departments", departments)
    val categorys = findItems(classOf[PersonCategory])
    put("categorys", categorys)
    super.editSetting(entity)
  }

  private def findItems[T <: Entity[_]](clazz: Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz)
    query.orderBy("name")
    val items = entityDao.search(query)
    items
  }
}
