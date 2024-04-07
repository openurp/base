[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="educationTypeSearchForm" action="!search" target="educationTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="educationType.code;代码"/]
      [@b.textfields names="educationType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="educationType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="educationTypelist" href="!search?orderBy=educationType.code&active=1"/]
  </div>
</div>
[@b.foot/]
