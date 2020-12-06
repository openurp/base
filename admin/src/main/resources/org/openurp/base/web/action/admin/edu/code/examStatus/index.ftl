[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="examStatusSearchForm" action="!search" target="examStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="examStatus.code;代码"/]
      [@b.textfields names="examStatus.name;名称"/]
      <input type="hidden" name="orderBy" value="examStatus.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="examStatuslist" href="!search?orderBy=examStatus.code"/]
  </div>
</div>
[@b.foot/]
