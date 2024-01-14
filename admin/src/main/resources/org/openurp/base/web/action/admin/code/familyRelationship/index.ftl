[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="familyRelationshipSearchForm" action="!search" target="familyRelationshiplist" title="ui.searchForm" theme="search"]
      [@b.textfields names="familyRelationship.code;代码"/]
      [@b.textfields names="familyRelationship.name;名称"/]
      [@b.textfields names="familyRelationship.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="familyRelationship.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="familyRelationshiplist" href="!search?orderBy=familyRelationship.code&active=1"/]
    </td>
  </tr>
</table>
[@b.foot/]
