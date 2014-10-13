[#ftl]
[@b.head/]
[@b.toolbar title="通用人员信息"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="personSearchForm" action="!search" target="personlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="person.code;学工号"/]
      [@b.textfields names="person.name;姓名"/]
      [@b.select name="person.category.id" label="人员分类" href=urp.service("/base/code/person-categories") empty="..." style="width:100px"/]  
      [@b.select name="person.department.id" label="所在部门" href=urp.service("/base/departments") empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="person.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="personlist" href="!search?orderBy=person.code"/]
    </td>
  </tr>
</table>
[@b.foot/]