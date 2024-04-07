[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="examStatusSearchForm" action="!search" target="examStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="examStatus.code;代码"/]
      [@b.textfields names="examStatus.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="examStatus.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="examStatuslist" href="!search?orderBy=examStatus.code&active=1"/]
  </div>
</div>
[@b.foot/]
