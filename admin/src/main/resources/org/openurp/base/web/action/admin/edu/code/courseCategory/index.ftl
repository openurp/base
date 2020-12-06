[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseCategorySearchForm" action="!search" target="courseCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseCategory.code;代码"/]
      [@b.textfields names="courseCategory.name;名称"/]
      <input type="hidden" name="orderBy" value="courseCategory.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseCategorylist" href="!search?orderBy=courseCategory.code"/]
  </div>
</div>
[@b.foot/]
