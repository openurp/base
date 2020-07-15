[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="politicalStatusSearchForm" action="!search" target="politicalStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="politicalStatus.code;代码"/]
      [@b.textfields names="politicalStatus.name;名称"/]
      [@b.textfields names="politicalStatus.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="politicalStatus.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="politicalStatuslist" href="!search?orderBy=politicalStatus.code"/]
    </div>
  </div>
[@b.foot/]
