[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="gradeTypeSearchForm" action="!search" target="gradeTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="gradeType.code;代码"/]
      [@b.textfields names="gradeType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="gradeType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="gradeTypelist" href="!search?orderBy=gradeType.code&active=1"/]
  </div>
</div>
[@b.foot/]
