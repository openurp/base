[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="bookTypeSearchForm" action="!search" target="bookTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="bookType.code;代码"/]
      [@b.textfields names="bookType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="bookType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="bookTypelist" href="!search?orderBy=bookType.code&active=1"/]
  </div>
</div>
[@b.foot/]
