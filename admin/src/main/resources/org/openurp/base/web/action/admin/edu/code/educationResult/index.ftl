[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="educationResultSearchForm" action="!search" target="educationResultlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="educationResult.code;代码"/]
      [@b.textfields names="educationResult.name;名称"/]
      <input type="hidden" name="orderBy" value="educationResult.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="educationResultlist" href="!search?orderBy=educationResult.code"/]
  </div>
</div>
[@b.foot/]
