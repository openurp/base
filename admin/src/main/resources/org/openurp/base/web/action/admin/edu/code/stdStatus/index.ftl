[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdStatusSearchForm" action="!search" target="stdStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdStatus.code;代码"/]
      [@b.textfields names="stdStatus.name;名称"/]
      <input type="hidden" name="orderBy" value="stdStatus.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdStatuslist" href="!search?orderBy=stdStatus.code"/]
  </div>
</div>
[@b.foot/]
