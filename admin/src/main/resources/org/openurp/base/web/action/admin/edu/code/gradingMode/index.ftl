[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="gradingModeSearchForm" action="!search" target="gradingModelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="gradingMode.code;代码"/]
      [@b.textfields names="gradingMode.name;名称"/]
      <input type="hidden" name="orderBy" value="gradingMode.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="gradingModelist" href="!search?orderBy=gradingMode.code"/]
  </div>
</div>
[@b.foot/]
