package org.openurp.base.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{ Department, School }

class SchoolAction extends RestfulAction[School]

class DepartmentAction extends RestfulAction[Department] 