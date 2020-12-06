[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="userCategorySearchForm" action="!search" target="userCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="userCategory.code;代码"/]
      [@b.textfields names="userCategory.name;名称"/]
      [@b.textfields names="userCategory.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="userCategory.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="userCategorylist" href="!search?orderBy=userCategory.code"/]
    </div>
  </div>
[@b.foot/]
