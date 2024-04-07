[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="degreeSearchForm" action="!search" target="degreelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="degree.code;代码"/]
      [@b.textfields names="degree.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="degree.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="degreelist" href="!search?orderBy=degree.code&active=1"/]
  </div>
</div>
[@b.foot/]
