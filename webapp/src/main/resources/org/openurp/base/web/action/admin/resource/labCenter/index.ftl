[#ftl]
[@b.head/]
[#include "../laboratory/nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="labCenterSearchForm" action="!search" target="labCenterlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="labCenter.name;名称"/]
      <input type="hidden" name="orderBy" value="labCenter.name"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="labCenterlist" href="!search?orderBy=labCenter.name"/]
  </div>
</div>
[@b.foot/]
