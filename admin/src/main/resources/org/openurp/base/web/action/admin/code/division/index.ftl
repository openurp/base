[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="divisionSearchForm" action="!search" target="divisionlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="division.code;代码"/]
      [@b.textfields names="division.name;名称"/]
      [@b.textfields names="division.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="division.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="divisionlist" href="!search?orderBy=division.code"/]
    </div>
  </div>
[@b.foot/]
