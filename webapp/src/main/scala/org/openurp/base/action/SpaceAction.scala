package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.Building
import org.openurp.base.Campus
import org.openurp.base.Department
import org.openurp.base.Room

class CampusAction extends RestfulAction[Campus]

class BuildingAction extends RestfulAction[Building]

class RoomAction extends RestfulAction[Room]