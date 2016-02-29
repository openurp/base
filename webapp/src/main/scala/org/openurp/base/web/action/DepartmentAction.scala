package org.openurp.base.web.action

import org.openurp.base.model.{ Department, School }
import org.beangle.webmvc.entity.action.RestfulAction

class SchoolAction extends RestfulAction[School]

class DepartmentAction extends RestfulAction[Department]