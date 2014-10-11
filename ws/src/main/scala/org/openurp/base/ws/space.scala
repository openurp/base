package org.openurp.base.ws

import org.beangle.webmvc.entity.action.AbstractEntityAction
import org.openurp.base.Building
import org.openurp.base.Campus
import org.openurp.base.Room

class CampusAction extends AbstractEntityAction[Campus]

class BuildingAction extends AbstractEntityAction[Building]

class RoomAction extends AbstractEntityAction[Room]