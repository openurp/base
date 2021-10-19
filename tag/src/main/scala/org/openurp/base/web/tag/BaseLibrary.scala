/*
 * Copyright (C) 2005, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.base.web.tag

import org.beangle.commons.lang.annotation.description
import org.beangle.template.api.{AbstractTagLibrary, IndexableIdGenerator}
import org.beangle.web.action.context.ActionContext
import org.beangle.webmvc.view.i18n.NullTextProvider

@description("Openurp Base 标签库")
class BaseLibrary extends AbstractTagLibrary {

  override def models(): AnyRef = {
    new BaseModels(this.buildComponentContext())
  }

  override def buildServices(): Map[String, AnyRef] = {
    val req = ActionContext.current.request
    val queryString = req.getQueryString
    val fullpath = if (null == queryString) req.getRequestURI else req.getRequestURI + queryString
    val idGenerator = new IndexableIdGenerator(String.valueOf(Math.abs(fullpath.hashCode)))
    val textProvider = ActionContext.current.textProvider.getOrElse(NullTextProvider)
    Map("idGenerator" -> idGenerator, "textProvider" -> textProvider)
  }
}
