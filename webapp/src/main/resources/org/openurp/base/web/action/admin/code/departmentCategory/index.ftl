[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="departmentCategorySearchForm" action="!search" target="departmentCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="departmentCategory.code;代码"/]
      [@b.textfields names="departmentCategory.name;名称"/]
      [@b.textfields names="departmentCategory.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="departmentCategory.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="departmentCategorylist" href="!search?orderBy=departmentCategory.code&active=1"/]
    </div>
  </div>
[@b.foot/]
