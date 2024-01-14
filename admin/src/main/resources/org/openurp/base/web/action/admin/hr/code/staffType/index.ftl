[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="staffTypeSearchForm" action="!search" target="staffTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="staffType.code;代码"/]
      [@b.textfields names="staffType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="staffType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="staffTypelist" href="!search?orderBy=staffType.code&active=1"/]
  </div>
</div>
[@b.foot/]
