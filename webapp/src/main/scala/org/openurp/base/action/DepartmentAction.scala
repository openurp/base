package org.openurp.base.action

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.code.Institution
import org.openurp.base.domain.DepartmentBean
import org.openurp.base.domain.SchoolBean
import org.openurp.base.Department
import org.openurp.base.School

class SchoolAction extends RestfulAction[School] 

class DepartmentAction extends RestfulAction[Department] 