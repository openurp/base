[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdAlterTypeSearchForm" action="!search" target="stdAlterTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdAlterType.code;代码"/]
      [@b.textfields names="stdAlterType.name;名称"/]
      <input type="hidden" name="orderBy" value="stdAlterType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdAlterTypelist" href="!search?orderBy=stdAlterType.code"/]
  </div>
</div>
[@b.foot/]
