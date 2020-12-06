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
package org.openurp.base.edu.web.helper

import java.time.LocalDate

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity

/**
 * 查询条件辅助类
 *
 * @author zhouqi 2017年5月9日
 *
 */
object QueryHelper {

  def addTemporalOn[T <: Entity[_]](builder: OqlBuilder[T], active: Option[Boolean]): OqlBuilder[T] = {
    active.foreach { active =>
      if (active) {
        builder.where(
          builder.alias + ".beginOn <= :now and (" + builder.alias + ".endOn is null or " + builder.alias + ".endOn >= :now)",
          LocalDate.now())
      } else {
        builder.where(
          "not (" + builder.alias + ".beginOn <= :now and (" + builder.alias + ".endOn is null or " + builder.alias + ".endOn >= :now))",
          LocalDate.now())
      }
    }

    builder
  }
}
