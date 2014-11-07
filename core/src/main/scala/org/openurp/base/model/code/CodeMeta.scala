/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY=_ without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base.model.code

import org.beangle.data.model.bean.IntIdBean
import org.beangle.data.model.SlowId

/**
 * 登记系统使用的基础代码
 * </p>
 * 这些代码的名称、英文名称和全称类名
 *
 * @author chaostone
 */
class CodeMeta extends IntIdBean with SlowId {

  var name: String = _

  /** 中文名称 */
  var title: String = _

  /** 类名 */
  var className: String = _

  /** 所在分类 */
  var category: CodeCategory = _

}
