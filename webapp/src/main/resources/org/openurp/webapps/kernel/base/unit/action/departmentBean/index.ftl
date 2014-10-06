[#ftl]
[@b.head/]
[@b.toolbar title="部门类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="departmentBeanSearchForm" action="!search" target="departmentBeanlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="departmentBean.name;部门名称"/]
      [@b.textfields names="departmentBean.code;代码"/]
      [@b.textfields names="departmentBean.indexno;序号"/]
      <input type="hidden" name="orderBy" value="departmentBean.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="departmentBeanlist" href="!search?orderBy=departmentBean.code"/]
    </td>
  </tr>
</table>
[@b.foot/]