[#ftl]
[@b.head/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="politicalStatusSearchForm" action="!search" target="politicalStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="politicalStatus.code;代码"/]
      [@b.textfields names="politicalStatus.name;名称"/]
      [@b.textfields names="politicalStatus.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="politicalStatus.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="politicalStatuslist" href="!search?orderBy=politicalStatus.code"/]</td>
  </tr>
</table>
[@b.foot/]
