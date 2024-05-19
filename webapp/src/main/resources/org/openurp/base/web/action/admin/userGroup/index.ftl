[#ftl]
[@b.head/]
[#include "../user-nav.ftl"/]

<div class="search-container">
    <div class="search-panel">
    [@b.form name="userGroupSearchForm" action="!search" target="userGrouplist" title="ui.searchForm" theme="search"]
      [@b.textfield name="userGroup.code" label="编码" /]
      [@b.textfield name="userGroup.name" label="名称"/]
      <input type="hidden" name="orderBy" value="userGroup.indexno"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="userGrouplist" href="!search?orderBy=userGroup.indexno"/]
    </div>
  </div>
[@b.foot/]
