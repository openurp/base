[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="gradeSearchForm" action="!search" target="gradelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="grade.code;代码"/]
      [@b.textfields names="grade.name;名称"/]
      <input type="hidden" name="orderBy" value="grade.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="gradelist" href="!search?orderBy=grade.code&active=1"/]
  </div>
</div>
[@b.foot/]
