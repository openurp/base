[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseCategorySearchForm" action="!search" target="courseCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseCategory.code;代码"/]
      [@b.textfields names="courseCategory.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="courseCategory.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseCategorylist" href="!search?orderBy=courseCategory.code&active=1"/]
  </div>
</div>
[@b.foot/]
