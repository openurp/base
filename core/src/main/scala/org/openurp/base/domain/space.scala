package org.openurp.base.domain

import org.beangle.data.model.IdGrowSlow
import org.beangle.data.model.bean.{ CodedBean, IntIdBean, NamedBean, TemporalOnBean, UpdatedBean }
import org.openurp.base.{ Building, Campus, Department, Room }
import org.openurp.base.code.RoomType

/**
 * 校区
 */
class CampusBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean
  with UpdatedBean with Campus with IdGrowSlow

/**
 * 建筑
 */
class BuildingBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean
  with UpdatedBean with Building with IdGrowSlow {
  /**
   * 所属校区
   */
  var campus: Campus = _
}

/**
 * 房间
 */
class RoomBean extends IntIdBean with CodedBean with NamedBean with TemporalOnBean
  with UpdatedBean with Room with IdGrowSlow {

  var department: Department = _

  var roomType: RoomType = _

  var campus: Campus = _

  var building: Building = _

}

