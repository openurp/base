[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="pressGradeSearchForm" action="!search" target="pressGradelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="pressGrade.code;代码"/]
      [@b.textfields names="pressGrade.name;名称"/]
      <input type="hidden" name="orderBy" value="pressGrade.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="pressGradelist" href="!search?orderBy=pressGrade.code"/]
  </div>
</div>
[@b.foot/]
