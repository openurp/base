[#ftl]
[@b.head/]
[#include "../department/nav.ftl"/]
[@b.toolbar title="部门变迁记录管理"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="transitionSearchForm" action="!search" target="transitionlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="transition.from.name" label="原部门"/]
      [@b.textfield name="transition.to.name" label="目标部门"/]
      <input type="hidden" name="orderBy" value="transition.effectiveOn"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="transitionlist" href="!search?orderBy=transition.effectiveOn desc"/]
    </div>
  </div>
[@b.foot/]
