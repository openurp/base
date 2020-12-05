[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="degreeSearchForm" action="!search" target="degreelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="degree.code;代码"/]
      [@b.textfields names="degree.name;名称"/]
      <input type="hidden" name="orderBy" value="degree.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="degreelist" href="!search?orderBy=degree.code"/]
  </div>
</div>
[@b.foot/]
