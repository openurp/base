package org.openurp.base.model

import java.{ util => ju }
import org.beangle.data.model.bean.{ CodedBean, LongIdBean, NamedBean, UpdatedBean }
import org.openurp.base.{ Department, Person }
import org.openurp.base.code.{ Country, Gender, IdType, PersonCategory }
import org.beangle.data.model.bean.IntIdBean
import org.openurp.base.code.Nation

/**
 * 通用人员信息
 */
class PersonBean extends LongIdBean with CodedBean with NamedBean with UpdatedBean with Person {
  /**Socail ID*/
  var sid: String = _
  var enName: String = _
  var idType: IdType = _
  var country: Country = _
  var department: Department = _
  var gender: Gender = _
  var nation: Nation = _
  var category: PersonCategory = _
  var email: String = _
  var birthday: ju.Date = _
  var mobile: String = _
  var address: String = _
  var remark: String = _
}