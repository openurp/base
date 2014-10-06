package org.openurp.base.domain.code

import org.beangle.data.model.annotation.code
import org.openurp.base.code.{ DisciplineCategory, Institution }
import org.openurp.platform.model.BaseCodeBean

/**
 * 科研机构
 * @see http://www.stats.edu.cn/rjgx/dm/%E6%99%AE%E9%80%9A%E9%AB%98%E6%A0%A1%E4%BB%A3%E7%A0%81.htm
 */
@code("ministry")
class InstitutionBean extends BaseCodeBean with Institution

/**
 * 学科门类
 * 参见国家推荐标准GB/T 13745-92
 * @see http://web.heuet.edu.cn/rsc/zhicheng2007/erjixuekemulu.doc
 */
@code("ministry")
class DisciplineCategoryBean extends BaseCodeBean with DisciplineCategory