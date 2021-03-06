/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.web.action.info.edu

import org.beangle.commons.collection.Order
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.entity.action.{EntityAction, ExportSupport}
import org.openurp.code.service.CodeService
import org.openurp.boot.edu.helper.ProjectSupport

abstract class ProjectRestfulAction[T <: Entity[_]] extends ActionSupport
  with EntityAction[T] with ExportSupport[T] with ProjectSupport {

  var codeService: CodeService = _

  override protected def getQueryBuilder: OqlBuilder[T] = {
    val builder: OqlBuilder[T] = OqlBuilder.from(entityName, simpleEntityName)
    populateConditions(builder)
    val entityType = entityDao.domain.getEntity(entityName).get
    entityType.getProperty("project") foreach { d =>
      builder.where(simpleEntityName + ".project = :project", getProject)
    }
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

}
