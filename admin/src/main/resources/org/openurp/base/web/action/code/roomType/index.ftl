[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="roomTypeSearchForm" action="!search" target="roomTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="roomType.code;代码"/]
      [@b.textfields names="roomType.name;名称"/]
      [@b.textfields names="roomType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="roomType.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="roomTypelist" href="!search?orderBy=roomType.code"/]
    </div>
  </div>
[@b.foot/]
