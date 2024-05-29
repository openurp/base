[#ftl]
[@b.head/]
[@b.toolbar title="辅修专业"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="majorSearchForm" action="!search" target="majorlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="major.name;名称"/]
      [@b.textfield name="major.institution.name" label="所属院校"  /]
      <input type="hidden" name="orderBy" value="major.id"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="majorlist" href="!search?orderBy=major.id"/]
    </div>
  </div>
[@b.foot/]
