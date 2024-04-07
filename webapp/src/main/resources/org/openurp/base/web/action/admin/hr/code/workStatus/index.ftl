[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="workStatusSearchForm" action="!search" target="workStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="workStatus.code;代码"/]
      [@b.textfields names="workStatus.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="workStatus.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="workStatuslist" href="!search?orderBy=workStatus.code&active=1"/]
  </div>
</div>
[@b.foot/]
