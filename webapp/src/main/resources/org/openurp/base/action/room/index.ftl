[#ftl]
[@b.head/]
[@b.toolbar title="房间类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="roomSearchForm" action="!search" target="roomlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="room.code;代码"/]
      [@b.textfields names="room.name;名称"/]
      <input type="hidden" name="orderBy" value="room.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="roomlist" href="!search?orderBy=room.code"/]
    </td>
  </tr>
</table>
[@b.foot/]