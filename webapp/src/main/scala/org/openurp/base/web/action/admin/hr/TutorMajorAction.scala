package org.openurp.base.web.action.admin.hr

import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.edu.model.{Direction, Major}
import org.openurp.base.hr.model.TutorMajor
import org.openurp.base.model.Project
import org.openurp.starter.web.support.ProjectSupport

class TutorMajorAction extends RestfulAction[TutorMajor], ProjectSupport {

  override protected def indexSetting(): Unit = {
    given project: Project = getProject

    put("majors", findInProject(classOf[Major]))
    super.indexSetting()
  }

  override protected def editSetting(entity: TutorMajor): Unit = {
    given project: Project = getProject

    put("majors", findInProject(classOf[Major]))
    put("directions", findInProject(classOf[Direction]))
    put("project", project)
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: TutorMajor): View = {
    val directionIds = getAll("direction.id", classOf[Long])
    val newDirections = entityDao.find(classOf[Direction], directionIds)
    entity.directions.clear()
    entity.directions.addAll(newDirections)
    super.saveAndRedirect(entity)
  }

}
