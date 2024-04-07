[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseTypeSearchForm" action="!search" target="courseTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseType.code;代码"/]
      [@b.textfields names="courseType.name;名称"/]
      [@b.select label="实践课"  name="courseType.practical" items={"1":"是","0":"否"}/]
      [@b.select label="专业课"  name="courseType.major" items={"1":"是","0":"否"}/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="courseType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseTypelist" href="!search?orderBy=courseType.code&active=1"/]
  </div>
</div>
[@b.foot/]
