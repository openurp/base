package org.openurp.base.model

import org.beangle.data.model.bean.{ CodedBean, HierarchicalBean, IntIdBean, NamedBean, TemporalOnBean }
import org.openurp.base.{ Department, School }
import org.openurp.base.code.Institution
/**
 * 学校
 */
class SchoolBean extends IntIdBean with CodedBean with NamedBean with School{
  var institution: Institution = _
}

/**
 * 部门
 */
class DepartmentBean extends IntIdBean with CodedBean with NamedBean
  with HierarchicalBean[Department] with Department
  with TemporalOnBean {
  var teaching = false
  var enName : String = _
  var shortName : String =_
}