[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="examTypeSearchForm" action="!search" target="examTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="examType.code;代码"/]
      [@b.textfields names="examType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="examType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="examTypelist" href="!search?orderBy=examType.code&active=1"/]
  </div>
</div>
[@b.foot/]
