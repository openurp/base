[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="eduCategorySearchForm" action="!search" target="eduCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="eduCategory.code;代码"/]
      [@b.textfields names="eduCategory.name;名称"/]
      [@b.textfields names="eduCategory.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="eduCategory.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="eduCategorylist" href="!search?orderBy=eduCategory.code&active=1"/]
    </div>
  </div>
[@b.foot/]
