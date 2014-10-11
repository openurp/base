[#ftl]
[@b.head/]
[@b.toolbar title="时间设置"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:210px">
    [@b.form name="timeSettingSearchForm" action="!search" target="timeSettinglist" title="ui.searchForm" theme="search"]
      [@b.textfields names="timeSetting.name;名称"/]
      <input type="hidden" name="orderBy" value="name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="timeSettinglist" href="!search?orderBy=id"/]
    </td>
  </tr>
</table>
[@b.foot/]