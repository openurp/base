[#ftl]
[@b.head/]
[#include "../semester_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stageSearchForm" action="!search" target="stagelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="calendarStage.name;名称"/]
    [/@]
    </div>
    <div class="search-list">[@b.div id="stagelist" href="!search?orderBy=calendarStage.id"/]
  </div>
</div>
[@b.foot/]
