[#ftl]
[@b.head/]
[@b.toolbar title="假日安排"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="holidaySearchForm" action="!search" target="holidaylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="holiday.name;名称"/]
      <input type="hidden" name="orderBy" value="holiday.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="holidaylist" href="!search?orderBy=holiday.id"/]
    </td>
  </tr>
</table>
[@b.foot/]