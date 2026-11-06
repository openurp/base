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

import org.beangle.commons.bean.Properties
import org.beangle.commons.collection.Order
import org.beangle.commons.json.{Json, JsonObject}
import org.beangle.commons.lang.Strings
import org.beangle.commons.text.i18n.Messages
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.json.JsonAPI.Context
import org.beangle.data.model.Entity
import org.beangle.data.model.meta.EntityType
import org.beangle.data.model.pojo.Updatable
import org.beangle.ems.app.log.WebBusinessLogger
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.she.webmvc.JsonAPIWS
import org.beangle.webmvc.annotation.{mapping, param, response}
import org.beangle.webmvc.context.{ActionContext, Params}
import org.openurp.base.model.{Campus, Department, Project, School}
import org.openurp.base.web.action.admin.SchoolSupport
import org.openurp.code.CodeBean
import org.openurp.starter.web.support.ProjectSupport

import java.time.Instant
import java.util.Locale
import scala.compiletime.uninitialized

/** 管理端 JSON 接口。列表、详情、保存、删除走 JsonAPI，页面仍由 web.action.admin 渲染。
 *
 * 参考 beangle-she 的 JsonAPIWS：https://github.com/beangle/she
 */
abstract class AdminJsonWS[T <: Entity[?]] extends JsonAPIWS[T] {

  override protected def setting(ctx: Context): Unit = {
    ctx.include[CodeBean]("id", "code", "name", "enName")
    ctx.include[School]("id", "code", "name")
    ctx.include[Department]("id", "code", "name", "shortName")
    ctx.include[Campus]("id", "code", "name")
    ctx.include[Project]("id", "code", "name")
    ctx.filters.include(entityClass, "*")
  }

  /** GET /api/base/admin/{resources}.jsonapi 与 /search.jsonapi 都返回分页列表。 */
  @response
  def index(): Json = search()

  protected def prepare(entity: T): Unit = {}

  /** 请求里带了和 FTL 表单相同的参数名时，才按页面规则改写集合。 */
  protected def formSubmitted(name: String): Boolean = {
    ActionContext.current.request.getParameterMap.containsKey(name)
  }

  override protected def persist(entity: T): JsonObject = {
    prepare(entity)
    super.persist(entity)
  }

  @response
  @mapping(value = "{id}", methods = "delete")
  def remove(@param("id") id: String): JsonObject = {
    val entity = getModel(id)
    if (null != entity) removeEntities(Seq(entity))
    val rs = new JsonObject()
    rs.add("removed", if (null == entity) 0 else 1)
    rs
  }

  protected def removeEntities(entities: Seq[T]): Unit = {
    if (entities.nonEmpty) entityDao.remove(entities)
  }
}

/** 按当前学校过滤，学校来自 EMS cookie，与页面上的 SchoolSupport 相同。 */
abstract class SchoolAdminJsonWS[T <: Entity[?]] extends AdminJsonWS[T], SchoolSupport {

  protected def schoolScoped(alias: String): OqlBuilder[T] = {
    val builder = OqlBuilder.from(entityClass, alias)
    builder.where(s"$alias.school=:school", getSchool)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }
}

/** 按当前项目过滤，并保留页面保存时的业务日志和缓存刷新。 */
abstract class ProjectAdminJsonWS[T <: Entity[?]] extends AdminJsonWS[T], ProjectSupport {

  var databus: DataEventBus = uninitialized
  var businessLogger: WebBusinessLogger = uninitialized

  override protected def getQueryBuilder: OqlBuilder[T] = {
    val builder = OqlBuilder.from(entityClass, simpleEntityName)
    populateConditions(builder)
    val entityType = entityDao.domain.getEntity(entityClass).get
    entityType.getProperty("project") foreach { _ =>
      builder.where(simpleEntityName + ".project = :project", getProject)
    }
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    builder.tailOrder(simpleEntityName + ".id")
  }

  override protected def persist(entity: T): JsonObject = {
    prepare(entity)
    saveMore(entity)
    toJson(entity)
  }

  override protected def removeEntities(entities: Seq[T]): Unit = {
    if (entities.nonEmpty) removeMore(entities)
  }

  protected def saveMore[X](entities: Entity[X]*): Unit = saveMore(entities)

  protected def saveMore[X](entities: Iterable[Entity[X]]): Unit = {
    val typeEntities = groupByType(entities)
    val operateCounts = typeEntities.map { x =>
      val newer = x._2.count(!_.persisted)
      (x._1, (newer, x._2.size - newer))
    }
    typeEntities foreach { case (entityType, values) =>
      if (entityType.getProperty("project").nonEmpty) {
        val project = getProject
        values foreach { e =>
          val p = Properties.get[AnyRef](e, "project")
          if null == p then Properties.set(e, "project", project)
        }
      }
      if (classOf[Updatable].isAssignableFrom(entityType.clazz)) {
        val now = Instant.now
        values foreach { e =>
          e.asInstanceOf[Updatable].updatedAt = now
        }
      }
    }

    //1. 先保存
    entityDao.saveOrUpdate(entities)
    //2. 记录日志
    typeEntities foreach { case (entityType, values) =>
      val messages = Messages(Locale.SIMPLIFIED_CHINESE)
      val counts = operateCounts(entityType)
      if (counts._1 > 0) {
        val e = values.head
        if (counts._1 == 1) {
          businessLogger.info(s"新增了${digest(messages, e, entityType)}", e.id, ActionContext.current.params)
        } else {
          val ids = Strings.abbreviate(values.map(_.id.toString).mkString(","), 1, 200)
          businessLogger.info(s"批量新增了${values.size}个${digest(messages, e, entityType)}...", ids, ActionContext.current.params)
        }
      }
      if (counts._2 > 0) {
        val e = values.head
        if (counts._2 == 1) {
          businessLogger.info(s"更新了${digest(messages, e, entityType)}", e.id, ActionContext.current.params)
        } else {
          val ids = Strings.abbreviate(values.map(_.id.toString).mkString(","), 1, 200)
          businessLogger.info(s"批量更新了${values.size}个${digest(messages, e, entityType)}...", ids, ActionContext.current.params)
        }
      }
      //3.发布消息
      if (entityType.cacheable) {
        values foreach { e => entityDao.evict(e) }
        databus.publish(DataEvent.update(values))
      }
    }
  }

  protected def removeMore(entities: Iterable[Entity[?]]): Unit = {
    val entityType = entityDao.domain.getEntity(entities.head.getClass).get
    //1.先删除
    entityDao.remove(entities)
    //2.记录日志
    val ids = Strings.abbreviate(entities.map(_.id.toString).mkString(","), 1, 200)
    val messages = Messages(Locale.SIMPLIFIED_CHINESE)
    businessLogger.info(s"删除了${entities.size}${digest(messages, entities.head, entityType)}...", ids, ActionContext.current.params)
    //3.发布消息
    if (entityType.cacheable) {
      databus.publish(DataEvent.remove(entities))
    }
  }

  private def digest(messages: Messages, entity: Entity[?], entityType: EntityType): String = {
    var str = messages.get(entityType.clazz, entityType.clazz.getSimpleName) + " "
    if (entityType.getProperty("name").nonEmpty) {
      str += Properties.get[String](entity, "name")
    }
    if (entityType.getProperty("code").nonEmpty) {
      str += ("(" + Properties.get[String](entity, "code") + ")")
    }
    str
  }

  private def groupByType[X](entities: Iterable[Entity[X]]): Map[EntityType, Iterable[Entity[X]]] = {
    entities.groupBy(e => entityDao.domain.getEntity(e.getClass).get)
  }
}
