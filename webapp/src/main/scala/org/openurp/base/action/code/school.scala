package org.openurp.base.action.code

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.{ IdType, PersonCategory, RoomType }

class IdTypeAction extends RestfulAction[IdType]

class RoomTypeAction extends RestfulAction[RoomType]

class PersonCategoryAction extends RestfulAction[PersonCategory]
