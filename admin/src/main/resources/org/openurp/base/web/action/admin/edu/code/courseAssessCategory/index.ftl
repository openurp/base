[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseAssessCategorySearchForm" action="!search" target="courseAssessCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseAssessCategory.code;代码"/]
      [@b.textfields names="courseAssessCategory.name;名称"/]
      <input type="hidden" name="orderBy" value="courseAssessCategory.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseAssessCategorylist" href="!search?orderBy=courseAssessCategory.code"/]
  </div>
</div>
[@b.foot/]
