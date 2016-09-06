[#ftl]
[@b.head/]
[@b.toolbar title="组织机构管理"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:210px">
    [@b.form name="departmentSearchForm" action="!search" target="departmentlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="department.code;代码"/]
      [@b.textfields names="department.name;名称"/]
      [@b.textfields names="department.indexno;序号"/]
      [@b.select name="department.teaching" label="是否教学部门" items={'true':'是','false':'否'} empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="department.code"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="departmentlist" href="!search?orderBy=department.code"/]
    </td>
  </tr>
</table>
[@b.foot/]