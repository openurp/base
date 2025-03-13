[#ftl]
[@b.head/]
[#include "../user-nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="userSearchForm" action="!search" target="userlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="user.code" label="学号/工号" maxlength="5000"/]
      [@b.textfields names="user.name;姓名"/]
      [@b.select name="user.category.id" label="人员分类" items=userCategories empty="..." style="width:100px"/]
      [@b.select name="user.department.id" label="所在部门" items=departments empty="..." style="width:100px"/]
      [@b.textfield name="groupName" label="用户组"/]
      <input type="hidden" name="orderBy" value="user.code desc"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="userlist" href="!search?orderBy=user.code desc"/]
    </div>
  </div>
[@b.foot/]
