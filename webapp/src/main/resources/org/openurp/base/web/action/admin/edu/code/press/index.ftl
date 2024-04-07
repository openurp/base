[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="pressSearchForm" action="!search" target="presslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="press.code;代码"/]
      [@b.textfields names="press.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="press.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="presslist" href="!search?orderBy=press.code&active=1"/]
  </div>
</div>
[@b.foot/]
