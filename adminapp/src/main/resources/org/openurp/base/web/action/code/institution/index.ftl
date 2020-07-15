[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="institutionSearchForm" action="!search" target="institutionlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="institution.code;代码"/]
      [@b.textfields names="institution.name;名称"/]
      [@b.textfields names="institution.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="institution.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="institutionlist" href="!search?orderBy=institution.code"/]
    </div>
  </div>
[@b.foot/]
