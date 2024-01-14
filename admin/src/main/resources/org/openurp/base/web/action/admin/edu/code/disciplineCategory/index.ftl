[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="disciplineCategorySearchForm" action="!search" target="disciplineCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="disciplineCategory.code;代码"/]
      [@b.textfields names="disciplineCategory.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="disciplineCategory.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="disciplineCategorylist" href="!search?orderBy=disciplineCategory.code&active=1"/]
  </div>
</div>
[@b.foot/]
