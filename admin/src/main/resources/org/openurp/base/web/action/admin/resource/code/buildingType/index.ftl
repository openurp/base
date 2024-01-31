[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="buildingTypeSearchForm" action="!search" target="buildingTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="buildingType.code;代码"/]
      [@b.textfields names="buildingType.name;名称"/]
      [@b.textfields names="buildingType.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="buildingType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="buildingTypelist" href="!search?orderBy=buildingType.code&active=1"/]
    </div>
  </div>
[@b.foot/]
