[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="teachingNatureSearchForm" action="!search" target="teachingNaturelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teachingNature.code;代码"/]
      [@b.textfields names="teachingNature.name;名称"/]
      <input type="hidden" name="orderBy" value="teachingNature.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teachingNaturelist" href="!search?orderBy=teachingNature.code"/]
  </div>
</div>
[@b.foot/]
