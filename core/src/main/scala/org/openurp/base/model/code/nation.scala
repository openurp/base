package org.openurp.base.model.code

import org.beangle.data.model.annotation.code
import org.openurp.base.code.{ Country, Division, Education, FamilyRelation, Gender, Language, Nation, PoliticalAffiliation }
import org.openurp.platform.model.BaseCodeBean
import org.openurp.base.code.DisciplineCategory

/**
 * 性别
 * 参见国家推荐标准 GB/T 2261.1-2003
 * @see http://www.gfjl.org/thread-70877-1-1.html
 * @see http://en.wikipedia.org/wiki/ISO/IEC_5218
 */
@code("nation")
class GenderBean extends BaseCodeBean with Gender

/**
 * 国家地区
 * 参见国家推荐标准 GB/T 2659-2000
 * @see http://en.wikipedia.org/wiki/ISO_3166-1
 * @see http://wenku.baidu.com/view/bd105c235901020207409cd1.html
 */
@code("nation")
class CountryBean extends BaseCodeBean with Country {
  var alpha3Code: String = _
  var alpha2Code: String = _
}

/**
 * 民族
 * 参见国家标准 GB 3304-91
 * @see http://www.gfjl.org/thread-74491-1-1.html
 */
@code("nation")
class NationBean extends BaseCodeBean with Nation {
  var alphaCode: String = _
}

/**
 * 政治面貌
 * 参见国家标准 GB 4762-84
 * @see http://www.gfjl.org/thread-79332-1-1.html
 */
@code("nation")
class PoliticalAffiliationBean extends BaseCodeBean with PoliticalAffiliation

/**
 * 语种
 * 参见国家推荐标准 GB/T 4880.2-2000
 * @see http://www.gfjl.org/thread-78200-1-1.html
 * @see http://zh.wikipedia.org/zh-cn/ISO_639-1%E4%BB%A3%E7%A0%81%E8%A1%A8
 * @see http://zh.wikipedia.org/wiki/ISO_639-2
 */
@code("nation")
class LanguageBean extends BaseCodeBean with Language

/**
 * 行政区划
 * 参见国家推荐标准 GB/T 2260-1999
 * @see http://www.gfjl.org/thread-83266-1-1.html
 */
@code("nation")
class DivisionBean extends BaseCodeBean with Division

/**
 * 学历
 * 参见国家推荐标准 GB/T 4658-2006
 * @see http://www.csres.com/detail/176312.html
 */
@code("nation")
class EducationBean extends BaseCodeBean with Education

/**
 * 人员关系
 * 参见国家推荐标准GB/T 4761-2008
 * @see http://www.sac.gov.cn/SACSearch/search?channelid=160591&templet=gjcxjg_detail.jsp&searchword=STANDARD_CODE=%27GB/T%204761-2008%27&XZ=T
 */
@code("nation")
class FamilyRelationBean extends BaseCodeBean with FamilyRelation
