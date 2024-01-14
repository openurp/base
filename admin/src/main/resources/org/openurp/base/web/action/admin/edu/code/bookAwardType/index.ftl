[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="bookAwardTypeSearchForm" action="!search" target="bookAwardTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="bookAwardType.code;代码"/]
      [@b.textfields names="bookAwardType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="bookAwardType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="bookAwardTypelist" href="!search?orderBy=bookAwardType.code&active=1"/]
  </div>
</div>
[@b.foot/]
