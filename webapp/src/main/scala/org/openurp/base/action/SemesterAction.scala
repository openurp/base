package org.openurp.base.action

import org.beangle.commons.lang.Numbers
import org.beangle.data.jpa.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.{ Calendar, Semester }
import org.openurp.base.domain.{ CalendarBean, SemesterBean }

class CalendarAction extends RestfulAction[Calendar]
class SemesterAction extends RestfulAction[Semester] 