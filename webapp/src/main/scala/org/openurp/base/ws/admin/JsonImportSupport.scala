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

import org.beangle.data.model.Entity
import org.beangle.data.model.util.ConvertPopulator
import org.beangle.she.webmvc.{EntityAction, ImportHelper}
import org.beangle.transfer.importer.listener.ForeignerListener
import org.beangle.transfer.importer.{DefaultEntityImporter, ImportResult, ImportSetting, Importer}
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.support.ActionSupport

/** 导入结果用 JSON 返回，供前端展示成功和失败行。 */
trait JsonImportSupport[T <: Entity[?]] {
  this: ActionSupport & EntityAction[T] =>

  @response
  def importData(): ImportResult = {
    val result = new ImportResult()
    val setting = new ImportSetting
    setting.domain = this.entityDao.domain
    setting.populator = new ConvertPopulator
    setting.reader = ImportHelper.buildReader()
    val entityClazz = setting.domain.getEntity(this.entityClass).get.clazz
    setting.addEntityClazz(entityClazz, simpleEntityName)
    configImport(setting)
    applyImportMode(setting)
    DefaultEntityImporter(setting).transfer(result)
    result
  }

  protected def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new ForeignerListener(entityDao))
  }

  /** 页面参数 importMode 在 configImport 之后生效，缺省或无法识别时保留原模式 */
  protected def applyImportMode(setting: ImportSetting): Unit = {
    get("importMode").foreach { name =>
      if Importer.Mode.values.exists(_.toString == name) then
        setting.mode = Importer.Mode.valueOf(name)
    }
  }
}
