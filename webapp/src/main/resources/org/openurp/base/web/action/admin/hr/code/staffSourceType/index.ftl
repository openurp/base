[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="staffSourceTypeSearchForm" action="!search" target="staffSourceTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="staffSourceType.code;代码"/]
      [@b.textfields names="staffSourceType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="staffSourceType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="staffSourceTypelist" href="!search?orderBy=staffSourceType.code&active=1"/]
  </div>
</div>
[@b.foot/]
