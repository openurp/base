[#ftl]
[@b.head/]
[@b.toolbar title="学校类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="schoolSearchForm" action="!search" target="schoollist" title="ui.searchForm" theme="search"]
      [@b.textfields names="school.code;代码"/]
      [@b.textfields names="school.name;名称"/]
      <input type="hidden" name="orderBy" value="school.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="schoollist" href="!search?orderBy=school.code"/]
    </td>
  </tr>
</table>
[@b.foot/]