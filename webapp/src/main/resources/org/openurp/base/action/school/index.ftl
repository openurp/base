[#ftl]
[@b.head/]
[@b.toolbar title="学校类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:200px">
    [@b.form name="schoolBeanSearchForm" action="!search" target="schoolBeanlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="schoolBean.code;代码"/]
      [@b.textfields names="schoolBean.name;名称"/]
      <input type="hidden" name="orderBy" value="schoolBean.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="schoolBeanlist" href="!search?orderBy=schoolBean.code"/]
    </td>
  </tr>
</table>
[@b.foot/]