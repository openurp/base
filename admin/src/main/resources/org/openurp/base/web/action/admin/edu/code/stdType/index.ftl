[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdTypeSearchForm" action="!search" target="stdTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdType.code;代码"/]
      [@b.textfields names="stdType.name;名称"/]
      [@b.textfields names="stdType.labelType;标签类型"/]
      <input type="hidden" name="orderBy" value="stdType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdTypelist" href="!search?orderBy=stdType.code"/]
  </div>
</div>
[@b.foot/]
