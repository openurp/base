package org.openurp.base.ws

import org.openurp.base.Building
import org.openurp.base.Campus
import org.openurp.base.Room
import org.beangle.webmvc.entity.action.RestfulService

class CampusAction extends RestfulService[Campus]

class BuildingAction extends RestfulService[Building]

class RoomAction extends RestfulService[Room]