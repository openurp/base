[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseNatureSearchForm" action="!search" target="courseNaturelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseNature.code;代码"/]
      [@b.textfields names="courseNature.name;名称"/]
      [@b.textfields names="courseNature.labelType;标签类型"/]
      <input type="hidden" name="orderBy" value="courseNature.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseNaturelist" href="!search?orderBy=courseNature.code"/]
  </div>
</div>
[@b.foot/]
