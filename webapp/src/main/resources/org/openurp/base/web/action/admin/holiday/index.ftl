[#ftl]
[@b.head/]
[#include "../semester_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stageSearchForm" action="!search" target="stagelist" title="ui.searchForm" theme="search"]
      [@b.textfield name="holiday.name" /]
      [@b.textfield name="holiday.startOn" /]
    [/@]
    </div>
    <div class="search-list">[@b.div id="stagelist" href="!search?orderBy=holiday.startOn desc"/]
  </div>
</div>
[@b.foot/]
