[#ftl]
[@b.head/]
[@b.toolbar title="人员关系类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:200px">
    [@b.form name="familyRelationSearchForm" action="!search" target="familyRelationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="familyRelation.code;代码"/]
      [@b.textfields names="familyRelation.name;名称"/]
      [@b.textfields names="familyRelation.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="familyRelation.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="familyRelationlist" href="!search?orderBy=familyRelation.code"/]
    </td>
  </tr>
</table>
[@b.foot/]