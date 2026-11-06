/*
 * Copyright (C) 2014, The OpenURP Software.
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

package org.openurp.base.ws.admin

import org.beangle.commons.collection.page.Page
import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.lang.Strings
import org.beangle.commons.lang.reflect.Reflections
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.json.JsonAPI
import org.beangle.data.json.JsonAPI.Context
import org.beangle.ems.app.log.WebBusinessLogger
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.she.webmvc.{PopulateHelper, QueryHelper}
import org.beangle.webmvc.annotation.{body, mapping, param, response}
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.ActionSupport
import org.openurp.base.web.action.admin.CodeHelper
import org.openurp.code.{Code, CodeBean}

import java.time.{Instant, LocalDate}
import scala.compiletime.uninitialized

/** 基础代码。路径 /api/base/admin/.../code/{category}.jsonapi，类别目录在同级的 /codes.jsonapi。 */
abstract class AdminCodeWS extends ActionSupport {

  var entityDao: org.beangle.data.dao.EntityDao = uninitialized
  var businessLogger: WebBusinessLogger = uninitialized
  var databus: DataEventBus = uninitialized
  var codeHelper: CodeHelper = uninitialized

  protected def codePackages: Set[String]

  @response
  def index(): Json = search()

  @response
  def search(): Json = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val q = OqlBuilder.from[Code](meta.entityType.entityName, "code")
    QueryHelper.populate(entityDao, q).limit(q).sort(q)
    val datas = entityDao.search(q)
    given ctx: Context = JsonAPI.context(ActionContext.current.params)
    ctx.filters.include(meta.entityType.clazz, "*")
    val rs = JsonAPI.newJson(datas.map(t => JsonAPI.create(t, "")))
    datas match {
      case p: Page[?] =>
        val metaJson = new JsonObject()
        val page = new JsonObject()
        rs.add("meta", metaJson)
        metaJson.add("page", page)
        page.add("number", p.pageIndex)
        page.add("size", QueryHelper.pageLimit.pageSize)
        page.add("total", p.totalItems)
      case _ =>
    }
    rs
  }

  @response
  @mapping("{id}")
  def info(@param("id") id: String): JsonObject = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val code = entityDao.get(meta.entityType.clazz.asInstanceOf[Class[Code]], id.toInt)
    toJson(meta, code)
  }

  @response
  @mapping(methods = "post")
  def save(@body body: JsonObject): JsonObject = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val code = Reflections.newInstance(meta.entityType.clazz).asInstanceOf[CodeBean]
    PopulateHelper.populate(code, meta.entityType, "code")
    if (!code.persisted && null == code.beginOn) code.beginOn = LocalDate.now
    persist(meta, code)
  }

  @response
  @mapping(value = "{id}", methods = "put,patch")
  def update(@param("id") id: String, @body body: JsonObject): JsonObject = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val code = entityDao.get(meta.entityType.clazz.asInstanceOf[Class[CodeBean]], id.toInt)
    PopulateHelper.populate(code, meta.entityType, "code")
    persist(meta, code)
  }

  @response
  @mapping(value = "{id}", methods = "delete")
  def remove(@param("id") id: String): JsonObject = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val code = entityDao.get(meta.entityType.clazz.asInstanceOf[Class[Code]], id.toInt)
    entityDao.remove(code)
    val ids = Strings.abbreviate(code.id.toString, 300)
    businessLogger.info(s"删除了基础代码:${code.code} ${code.name}", ids, ActionContext.current.params)
    databus.publish(DataEvent.remove(code))
    val rs = new JsonObject()
    rs.add("removed", 1)
    rs
  }

  @response
  def catalog(): Seq[JsonObject] = {
    codeHelper.getMetas(codePackages).map { meta =>
      val item = new JsonObject()
      item.add("code", meta.code)
      item.add("name", meta.name)
      item
    }.toSeq
  }

  private def persist(meta: org.openurp.base.web.action.admin.CodeMeta, code: CodeBean): JsonObject = {
    code.updatedAt = Instant.now
    entityDao.saveOrUpdate(code)
    businessLogger.info(s"保存了基础代码:${code.code} ${code.name}", code.id, ActionContext.current.params)
    databus.publish(DataEvent.update(code))
    toJson(meta, code)
  }

  private def toJson(meta: org.openurp.base.web.action.admin.CodeMeta, code: Code): JsonObject = {
    given ctx: Context = JsonAPI.context(ActionContext.current.params)
    ctx.filters.include(meta.entityType.clazz, "*")
    JsonAPI.newJson(JsonAPI.create(code, ""))
  }
}
