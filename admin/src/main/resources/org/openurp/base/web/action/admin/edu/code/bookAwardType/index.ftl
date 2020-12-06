[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="bookAwardTypeSearchForm" action="!search" target="bookAwardTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="bookAwardType.code;代码"/]
      [@b.textfields names="bookAwardType.name;名称"/]
      <input type="hidden" name="orderBy" value="bookAwardType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="bookAwardTypelist" href="!search?orderBy=bookAwardType.code"/]
  </div>
</div>
[@b.foot/]
