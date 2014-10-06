package org.openurp.base.domain.code

import org.beangle.data.model.annotation.code
import org.openurp.base.code.{ IdType, PersonCategory, RoomType }
import org.openurp.platform.model.BaseCodeBean

/**
 * 证件类型
 */
@code("school")
class IdTypeBean extends BaseCodeBean with IdType

/**
 * 房间类型
 */
@code("school")
class RoomTypeBean extends BaseCodeBean with RoomType

/**
 * 人员分类
 */
@code("school")
class PersonCategoryBean extends BaseCodeBean with PersonCategory
