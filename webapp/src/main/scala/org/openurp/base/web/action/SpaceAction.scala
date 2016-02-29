package org.openurp.base.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Building
import org.openurp.base.model.Campus
import org.openurp.base.model.Room

class CampusAction extends RestfulAction[Campus]

class BuildingAction extends RestfulAction[Building]

class RoomAction extends RestfulAction[Room]