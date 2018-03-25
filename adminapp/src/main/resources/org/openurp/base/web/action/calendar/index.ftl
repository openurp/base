[#ftl]
[@b.head/]
[@b.toolbar title="教学日历方案类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="calendarSearchForm" action="!search" target="calendarlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="calendar.code;代码"/]
      [@b.textfields names="calendar.name;名称"/]
      <input type="hidden" name="orderBy" value="code"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="calendarlist" href="!search?orderBy=code"/]
    </td>
  </tr>
</table>
[@b.foot/]
