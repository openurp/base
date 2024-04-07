[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdTypeSearchForm" action="!search" target="stdTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdType.code;代码"/]
      [@b.textfields names="stdType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="stdType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdTypelist" href="!search?orderBy=stdType.code&active=1"/]
  </div>
</div>
[@b.foot/]
