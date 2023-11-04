[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="staffSourceTypeSearchForm" action="!search" target="staffSourceTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="staffSourceType.code;代码"/]
      [@b.textfields names="staffSourceType.name;名称"/]
      <input type="hidden" name="orderBy" value="staffSourceType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="staffSourceTypelist" href="!search?orderBy=staffSourceType.code"/]
  </div>
</div>
[@b.foot/]
