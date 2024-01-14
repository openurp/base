[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="divisionSearchForm" action="!search" target="divisionlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="division.code;代码"/]
      [@b.textfields names="division.name;名称"/]
      [@b.textfields names="division.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="division.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="divisionlist" href="!search?orderBy=division.code&active=1"/]
    </div>
  </div>
[@b.foot/]
