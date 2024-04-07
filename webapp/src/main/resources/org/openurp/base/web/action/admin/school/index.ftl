[#ftl]
[@b.head/]
[@b.toolbar title="学校类型"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="schoolSearchForm" action="!search" target="schoollist" title="ui.searchForm" theme="search"]
      [@b.textfields names="school.code;代码"/]
      [@b.textfields names="school.name;名称"/]
      <input type="hidden" name="orderBy" value="school.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="schoollist" href="!search?orderBy=school.code&active=1"/]
    </div>
  </div>
[@b.foot/]
