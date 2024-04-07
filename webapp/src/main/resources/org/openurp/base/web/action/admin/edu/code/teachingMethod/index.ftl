[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="teachingMethodSearchForm" action="!search" target="teachingMethodlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teachingMethod.code;代码"/]
      [@b.textfields names="teachingMethod.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="teachingMethod.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teachingMethodlist" href="!search?orderBy=teachingMethod.code&active=1"/]
  </div>
</div>
[@b.foot/]
