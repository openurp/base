[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="degreeLevelSearchForm" action="!search" target="degreeLevellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="degreeLevel.code;代码"/]
      [@b.textfields names="degreeLevel.name;名称"/]
      <input type="hidden" name="orderBy" value="degreeLevel.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="degreeLevellist" href="!search?orderBy=degreeLevel.code"/]
  </div>
</div>
[@b.foot/]
