package org.openurp.base.domain

import org.beangle.data.model.SlowId
import org.beangle.data.model.bean.{ CodedBean, IntIdBean, NamedBean, TemporalOnBean, UpdatedBean }
import org.openurp.base.{ Building, Campus, Department, Room }
import org.openurp.base.code.RoomType

/**
 * 校区
 */
class CampusBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean
  with UpdatedBean with Campus with SlowId with Cloneable {
  var enName: String = _
  var shortName: String = _
}

/**
 * 建筑
 */
class BuildingBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean
  with UpdatedBean with Building with SlowId {
  /**
   * 所属校区
   */
  var campus: Campus = _
  var enName: String = _
  var shortName: String = _
}

/**
 * 房间
 */
class RoomBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean
  with UpdatedBean with Room with SlowId {

  var department: Department = _

  var roomType: RoomType = _

  var campus: Campus = _

  var building: Building = _

  var floor: Int = _

  var capacity: Int = _

}

