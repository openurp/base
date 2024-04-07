[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseTakeTypeSearchForm" action="!search" target="courseTakeTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseTakeType.code;代码"/]
      [@b.textfields names="courseTakeType.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="courseTakeType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseTakeTypelist" href="!search?orderBy=courseTakeType.code&active=1"/]
  </div>
</div>
[@b.foot/]
