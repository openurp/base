[#ftl]
[@b.head/]
[@b.toolbar title="通用人员信息"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="userSearchForm" action="!search" target="userlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="user.code;学工号"/]
      [@b.textfields names="user.name;姓名"/]
      [@b.select name="user.category.id" label="人员分类" href=urp.service("/base/code/user-categories") empty="..." style="width:100px"/]  
      [@b.select name="user.department.id" label="所在部门" href=urp.service("/base/departments") empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="user.code"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="userlist" href="!search?orderBy=user.code"/]
    </td>
  </tr>
</table>
[@b.foot/]