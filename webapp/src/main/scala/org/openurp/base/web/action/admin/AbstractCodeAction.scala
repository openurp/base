package org.openurp.base.web.action.admin

import org.beangle.commons.bean.Initializing
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings.unCamel
import org.beangle.commons.lang.reflect.Reflections
import org.beangle.commons.text.i18n.Messages
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.model.meta.EntityType
import org.beangle.data.orm.{OrmEntityType, OrmStructType}
import org.beangle.web.action.annotation.{mapping, param}
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.execution.MappingHandler
import org.beangle.webmvc.support.helper.{PopulateHelper, QueryHelper}
import org.openurp.code.{Code, CodeBean}

import java.time.{Instant, LocalDate}
import java.util.Locale

/** 基础代码编辑类
 */
abstract class AbstractCodeAction extends ActionSupport {

  var entityDao: EntityDao = _

  var codeHelper: CodeHelper = _

  private def nomalize(name: String): String = {
    val loc = name.lastIndexOf('.')
    val shortName = if (loc < 0) name else name.substring(loc + 1)
    unCamel(shortName, '-')
  }

  def search(): View = {
    val category = get("category", "")
    codeHelper.getMeta(category) match
      case None =>
        forward("wrong-code")
      case Some(meta) =>
        val q = getQueryBuilder(meta)
        put("codes", entityDao.search(q))
        val propertyCnt = meta.entityType.asInstanceOf[OrmStructType].properties.size
        forward(s"${meta.code}/list,list")
  }

  private def getQueryBuilder(meta: CodeMeta): OqlBuilder[Code] = {
    val q = OqlBuilder.from[Code](meta.entityType.entityName, "code")
    QueryHelper.populate(q).limit(q).sort(q)
    q
  }

  @mapping(value = "{id}/edit")
  def edit(@param("id") id: String): View = {
    val meta = codeHelper.loadMeta(get("category", ""))
    editSetting(meta, getCode(meta, id.toInt))
    forward(s"${meta.code}/form,form")
  }

  @mapping(value = "new")
  def editNew(): View = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val code = Reflections.newInstance(meta.entityType.clazz).asInstanceOf[CodeBean]
    PopulateHelper.populate(code, meta.entityType, "code")
    editSetting(meta, code)
    forward(s"${meta.code}/form,form")
  }

  private def editSetting(meta: CodeMeta, code: Code): Unit = {
    if !code.persisted then code.asInstanceOf[CodeBean].beginOn = LocalDate.now
    put("code", code)
    put("meta", meta)
    try {
      val method = getClass.getMethod("edit" + meta.entityType.clazz.getSimpleName)
      method.setAccessible(true)
      method.invoke(this)
    } catch {
      case e: Exception =>
    }
  }

  @mapping(method = "post")
  def save(): View = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val code = Reflections.newInstance(meta.entityType.clazz).asInstanceOf[CodeBean]
    PopulateHelper.populate(code, meta.entityType, "code")
    persist(code)
  }

  @mapping(value = "{id}", method = "put")
  def update(@param("id") id: String): View = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val entity = getCode(meta, id.toInt)
    persist(entity)
  }

  private def persist(code: Code): View = {
    try {
      code.asInstanceOf[CodeBean].updatedAt = Instant.now
      entityDao.saveOrUpdate(code)
      redirect("search", "info.save.success")
    } catch {
      case e: Exception =>
        val mapping = ActionContext.current.handler.asInstanceOf[MappingHandler].mapping
        val redirectTo = mapping.method.getName match {
          case "save" => "editNew"
          case "update" => "edit"
        }
        logger.info("saveAndRedirect failure", e)
        redirect(redirectTo, "info.save.failure")
    }
  }

  @mapping(method = "delete")
  def remove(): View = {
    val meta = codeHelper.loadMeta(get("category", ""))
    val codes = entityDao.find(meta.entityType.clazz.asInstanceOf[Class[Code]], getIntIds("code"))
    try {
      entityDao.remove(codes)
      redirect("search", "info.remove.success")
    } catch {
      case e: Exception =>
        logger.info("remove failure", e)
        redirect("search", "info.delete.failure")
    }
  }

  def info(): View = {
    val meta = codeHelper.loadMeta(get("category", ""))
    put("code", getCode(meta, getInt("id", 0)))
    forward(s"${meta.code}/info,info")
  }

  private def getCode(meta: CodeMeta, id: Int): Code = {
    entityDao.get(meta.entityType.clazz.asInstanceOf[Class[Code]], id)
  }
}


