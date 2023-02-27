[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="gradeSearchForm" action="!search" target="graduateSeasonList" title="ui.searchForm" theme="search"]
      [@b.textfields names="graduateSeason.code;代码"/]
      [@b.textfields names="graduateSeason.name;名称"/]
      <input type="hidden" name="orderBy" value="graduateSeason.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="graduateSeasonList" href="!search?orderBy=graduateSeason.code"/]
  </div>
</div>
[@b.foot/]
