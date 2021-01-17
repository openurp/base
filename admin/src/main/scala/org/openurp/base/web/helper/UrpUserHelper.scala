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
package org.openurp.base.web.helper

import javax.sql.DataSource
import org.beangle.commons.bean.Initializing
import org.beangle.commons.codec.digest.Digests
import org.beangle.ems.app.datasource.AppDataSourceFactory
import org.openurp.base.code.model.UserCategory
import org.openurp.base.edu.model.Teacher
import org.springframework.jdbc.core.JdbcTemplate

class UrpUserHelper extends Initializing {

  var platformDataSource: DataSource = _

  override def init(): Unit = {
    val ds = new AppDataSourceFactory()
    ds.name = "platform"
    ds.init()
    platformDataSource = ds.result
  }

  def createTeacherUser(teacher: Teacher): Unit = {
    val category = new UserCategory()
    category.id = URPUserCategory.Teacher
    val password = "123456"
    val template = new JdbcTemplate(platformDataSource)
    val orgId = template.queryForObject("select id from cfg.orgs", classOf[Integer])
    val domainId = template.queryForObject("select id from cfg.domains", classOf[Integer])
    val teacherRoleId = template.queryForObject("select id from usr.roles where domain_id=? and name=?", classOf[Long], domainId, "教师")
    createAccount(orgId, domainId, teacher.user.code, teacher.user.name, password, category.id, teacherRoleId)
  }

  private def createAccount(orgId: Int, domainId: Int, code: String, name: String, password: String, categoryId: Int, roleId: Long): Unit = {
    val template = new JdbcTemplate(platformDataSource)
    val userIds = template.queryForList("select id from usr.users where org_id=" + orgId + " and code=? ", classOf[Long], code)
    var userId: java.lang.Long = null
    if (userIds.isEmpty) {
      userId = template.queryForObject("select datetime_id()", classOf[java.lang.Long])
      template.update(
        "insert into usr.users (id,code,name,org_id,category_id,updated_at,begin_on)"
          + "values(?,?,?,?,?,now(),current_date);",
        userId, code, name, orgId, categoryId); //"{MD5}" + EncryptUtil.encode(password),
    } else {
      userId = userIds.get(0)
    }

    val accountCount = template.queryForObject("select count(*) from usr.accounts where user_id=? and domain_id=? ", classOf[Int],
      userId, domainId)
    if (accountCount == 0) {
      val accountId = template.queryForObject("select datetime_id()", classOf[java.lang.Long])
      template.update(
        "insert into usr.accounts (id,user_id,domain_id,password,passwd_expired_on,locked,enabled,begin_on,updated_at)"
          + "values(?,?,?,?,current_date+180,false,true,current_date,now());",
        accountId, userId, domainId, "{SHA1}" + Digests.sha1Hex(password))
    }
    val roleCount = template.queryForObject("select count(*) from usr.role_members where user_id=? and role_id=? ", classOf[Int],
      userId, roleId)
    if (roleCount == 0) {
      template.update("insert into usr.role_members(id,user_id,role_id,is_member,is_granter,is_manager,updated_at)"
        + "values(datetime_id(),?,?,true,false,false,current_date);", userId, roleId)
    }
  }

}
