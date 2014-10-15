[#ftl]
[@b.head/]
[@b.toolbar title="政治面貌"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="politicalAffiliationSearchForm" action="!search" target="politicalAffiliationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="politicalAffiliation.code;代码"/]
      [@b.textfields names="politicalAffiliation.name;名称"/]
      [@b.textfields names="politicalAffiliation.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="politicalAffiliation.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="politicalAffiliationlist" href="!search?orderBy=politicalAffiliation.code"/]</td>
  </tr>
</table>
[@b.foot/]