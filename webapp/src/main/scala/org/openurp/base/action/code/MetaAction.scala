package org.openurp.base.action.code

import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.domain.code.CodeMeta
import org.openurp.base.domain.code.CodeCategory

class MetaAction extends RestfulAction[CodeMeta] 