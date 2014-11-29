package org.openurp.base.ds

import org.openurp.base.Building
import org.openurp.base.Campus
import org.openurp.base.Room
import org.beangle.webmvc.entity.action.RestfulService

class CampusWS extends RestfulService[Campus]

class BuildingWS extends RestfulService[Building]

class RoomWS extends RestfulService[Room]