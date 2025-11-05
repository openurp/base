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

package org.openurp.base.web.action.admin

import org.beangle.commons.bean.Properties
import org.beangle.commons.collection.Order
import org.beangle.commons.lang.Strings
import org.beangle.commons.text.i18n.Messages
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity
import org.beangle.data.model.meta.EntityType
import org.beangle.data.model.pojo.Updated
import org.beangle.ems.app.web.WebBusinessLogger
import org.beangle.event.bus.{DataEvent, DataEventBus}
import org.beangle.webmvc.context.ActionContext
import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.view.View
import org.openurp.starter.web.support.ProjectSupport

import java.time.Instant
import java.util.Locale

abstract class ProjectRestfulAction[T <: Entity[_]] extends RestfulAction[T], ProjectSupport {

  var databus: DataEventBus = _
  var businessLogger: WebBusinessLogger = _

  override protected def getQueryBuilder: OqlBuilder[T] = {
    val builder = OqlBuilder.from(entityClass, simpleEntityName)
    populateConditions(builder)
    val entityType = entityDao.domain.getEntity(entityClass).get
    entityType.getProperty("project") foreach { d =>
      builder.where(simpleEntityName + ".project = :project", getProject)
    }
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
    builder.tailOrder(simpleEntityName + ".id")
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
        entities foreach { e =>
          val p = Properties.get[AnyRef](e, "project")
          if null == p then Properties.set(e, "project", project)
        }
      }
      if (classOf[Updated].isAssignableFrom(entityType.clazz)) {
        val now = Instant.now
        entities foreach { e =>
          e.asInstanceOf[Updated].updatedAt = now
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
        //清楚本地缓存
        entities foreach { e => entityDao.evict(e) }
        //发布更新消息
        databus.publish(DataEvent.update(values))
      }
    }
  }

  protected def removeMore(entities: Iterable[Entity[_]]): Unit = {
    val entityType = entityDao.domain.getEntity(entities.head.getClass).get
    //1.先删除
    entityDao.remove(entities)
    //2.记录日志
    val ids = Strings.abbreviate(entities.map(_.id.toString).mkString(","), 1, 200)
    val messages = Messages(Locale.SIMPLIFIED_CHINESE)
    businessLogger.info(s"删除了${entities.size}${digest(messages, entities.head, entityType)}...", ids, ActionContext.current.params)
    //3.发布消息
    if (entityType.cacheable) {
      //发布删除消息
      databus.publish(DataEvent.remove(entities))
    }
  }

  override protected def saveAndRedirect(entity: T): View = {
    saveMore(entity)
    redirect("search", "info.save.success")
  }

  override protected def removeAndRedirect(entities: Seq[T]): View = {
    removeMore(entities)
    redirect("search", "info.remove.success")
  }

  private def digest(messages: Messages, entity: Entity[_], entityType: EntityType): String = {
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
