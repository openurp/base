package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.{ Country, Gender, IdType, PersonCategory }
import org.openurp.base.domain.{ DepartmentBean, PersonBean }
import org.openurp.base.Person

class PersonAction extends RestfulAction[Person] 
