[#ftl]
[@b.head/]
[@b.toolbar title="学历类型"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="educationSearchForm" action="!search" target="educationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="education.code;代码"/]
      [@b.textfields names="education.name;名称"/]
      [@b.textfields names="education.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="education.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="educationlist" href="!search?orderBy=education.code"/]
    </div>
  </div>
[@b.foot/]
