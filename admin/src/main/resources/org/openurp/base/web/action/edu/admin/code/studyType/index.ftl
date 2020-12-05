[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="studyTypeSearchForm" action="!search" target="studyTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="studyType.code;代码"/]
      [@b.textfields names="studyType.name;名称"/]
      <input type="hidden" name="orderBy" value="studyType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="studyTypelist" href="!search?orderBy=studyType.code"/]
  </div>
</div>
[@b.foot/]
