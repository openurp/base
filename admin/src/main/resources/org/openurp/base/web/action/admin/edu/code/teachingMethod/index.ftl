[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="teachingMethodSearchForm" action="!search" target="teachingMethodlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teachingMethod.code;代码"/]
      [@b.textfields names="teachingMethod.name;名称"/]
      [@b.textfields names="teachingMethod.labelType;标签类型"/]
      <input type="hidden" name="orderBy" value="teachingMethod.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teachingMethodlist" href="!search?orderBy=teachingMethod.code"/]
  </div>
</div>
[@b.foot/]
