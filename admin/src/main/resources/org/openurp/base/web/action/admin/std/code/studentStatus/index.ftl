[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="studentStatusSearchForm" action="!search" target="studentStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="studentStatus.code;代码"/]
      [@b.textfields names="studentStatus.name;名称"/]
      <input type="hidden" name="orderBy" value="studentStatus.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="studentStatuslist" href="!search?orderBy=studentStatus.code"/]
  </div>
</div>
[@b.foot/]
