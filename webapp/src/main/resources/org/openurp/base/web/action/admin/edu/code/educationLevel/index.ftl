[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="educationLevelSearchForm" action="!search" target="educationLevellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="educationLevel.code;代码"/]
      [@b.textfields names="educationLevel.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="educationLevel.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="educationLevellist" href="!search?orderBy=educationLevel.code&active=1"/]
  </div>
</div>
[@b.foot/]
