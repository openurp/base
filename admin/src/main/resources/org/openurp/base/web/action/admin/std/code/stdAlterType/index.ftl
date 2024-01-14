[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdAlterTypeSearchForm" action="!search" target="stdAlterTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdAlterType.code;代码"/]
      [@b.textfields names="stdAlterType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="stdAlterType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdAlterTypelist" href="!search?orderBy=stdAlterType.code&active=1"/]
  </div>
</div>
[@b.foot/]
