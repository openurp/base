[#ftl]
[@b.head/]
[@b.toolbar title="通用人员信息"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="personBeanSearchForm" action="!search" target="personBeanlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="personBean.name;姓名"/]
      [@b.textfields names="personBean.code;学工号"/]
      <input type="hidden" name="orderBy" value="personBean.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="personBeanlist" href="!search?orderBy=personBean.code"/]
    </td>
  </tr>
</table>
[@b.foot/]