[#ftl]
[@b.head/]
[#include "../semester_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="datalist" title="ui.searchForm" theme="search"]
      [@b.textfields names="schoolYear.name;名称"/]
      <input type="hidden" name="orderBy" value="name"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="datalist" href="!search?orderBy=name"/]
  </div>
</div>
[@b.foot/]
