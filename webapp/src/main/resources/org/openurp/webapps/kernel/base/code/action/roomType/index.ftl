[#ftl]
[@b.head/]
[@b.toolbar title="房间类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:200px">
    [@b.form name="roomTypeSearchForm" action="!search" target="roomTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="roomType.name;房间类型"/]
      [@b.textfields names="roomType.code;代码"/]
      [@b.textfields names="roomType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="roomType.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="roomTypelist" href="!search?orderBy=roomType.code"/]
    </td>
  </tr>
</table>
[@b.foot/]