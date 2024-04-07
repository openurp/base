[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="bookCategorySearchForm" action="!search" target="bookCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="bookCategory.code;代码"/]
      [@b.textfields names="bookCategory.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="bookCategory.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="bookCategorylist" href="!search?orderBy=bookCategory.code&active=1"/]
  </div>
</div>
[@b.foot/]
