[#ftl]
[@b.head/]
[@b.toolbar title="人员关系类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="familyRelationshipSearchForm" action="!search" target="familyRelationshiplist" title="ui.searchForm" theme="search"]
      [@b.textfields names="familyRelationship.code;代码"/]
      [@b.textfields names="familyRelationship.name;名称"/]
      [@b.textfields names="familyRelationship.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="familyRelationship.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="familyRelationshiplist" href="!search?orderBy=familyRelationship.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
