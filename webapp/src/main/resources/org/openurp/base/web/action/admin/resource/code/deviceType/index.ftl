[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="deviceTypeSearchForm" action="!search" target="deviceTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="deviceType.code;代码"/]
      [@b.textfields names="deviceType.name;名称"/]
      [@b.textfields names="deviceType.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="deviceType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="deviceTypelist" href="!search?orderBy=deviceType.code&active=1"/]
    </div>
  </div>
[@b.foot/]
