[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="userCategorySearchForm" action="!search" target="userCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="userCategory.code;代码"/]
      [@b.textfields names="userCategory.name;名称"/]
      [@b.textfields names="userCategory.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="userCategory.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="userCategorylist" href="!search?orderBy=userCategory.code&active=1"/]
    </div>
  </div>
[@b.foot/]
