[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="examModeSearchForm" action="!search" target="examModelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="examMode.code;代码"/]
      [@b.textfields names="examMode.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="examMode.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="examModelist" href="!search?orderBy=examMode.code&active=1"/]
  </div>
</div>
[@b.foot/]
