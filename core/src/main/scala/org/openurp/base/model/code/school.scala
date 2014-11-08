package org.openurp.base.model.code

import org.beangle.data.model.annotation.code
import org.openurp.base.code.{ IdType, PersonCategory, RoomType }
import org.openurp.platform.model.BaseCodeBean
import org.openurp.base.code.TeacherUnitType
import org.openurp.base.code.TeacherType
import org.openurp.base.code.TutorType
import org.openurp.base.code.TeacherState

/**
 * 证件类型
 */
@code("school")
class IdTypeBean extends BaseCodeBean with IdType

/**
 * 房间类型
 */
@code("school")
class RoomTypeBean extends BaseCodeBean with RoomType

/**
 * 人员分类
 */
@code("school")
class PersonCategoryBean extends BaseCodeBean with PersonCategory

/**
 * 教职工类别
 * 
 * @author chaostone
 * @since 2005-9-7
 */
@code("school")
class TeacherTypeBean extends BaseCodeBean with TeacherType {
  
  var parttime: Boolean = _

}



/**
 * 教师在职状态
 * 
 * @author zhouqi
 * @since 2005-9-7
 */
@code("school")
class TeacherStateBean extends BaseCodeBean with TeacherState{
}


/**
 * 外聘教师单位类别
 */
@code("school")
class TeacherUnitTypeBean extends BaseCodeBean with TeacherUnitType{
}


/**
 * 导师类型
 * @author chaostone
 * @since 2005-9-7
 */
@code("school")
class TutorTypeBean extends BaseCodeBean with TutorType{
  
}