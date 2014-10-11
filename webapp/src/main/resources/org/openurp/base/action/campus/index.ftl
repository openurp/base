[#ftl]
[@b.head/]
[@b.toolbar title="校区类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="campusSearchForm" action="!search" target="campuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="campus.code;代码"/]
      [@b.textfields names="campus.name;名称"/]
      <input type="hidden" name="orderBy" value="campus.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="campuslist" href="!search?orderBy=campus.code"/]
    </td>
  </tr>
</table>
[@b.foot/]