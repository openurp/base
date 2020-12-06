[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="pressSearchForm" action="!search" target="presslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="press.code;代码"/]
      [@b.textfields names="press.name;名称"/]
      <input type="hidden" name="orderBy" value="press.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="presslist" href="!search?orderBy=press.code"/]
  </div>
</div>
[@b.foot/]
