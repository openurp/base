[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="roomTypeSearchForm" action="!search" target="roomTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="roomType.code;代码"/]
      [@b.textfields names="roomType.name;名称"/]
      [@b.textfields names="roomType.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="roomType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="roomTypelist" href="!search?orderBy=roomType.code&active=1"/]
    </div>
  </div>
[@b.foot/]
