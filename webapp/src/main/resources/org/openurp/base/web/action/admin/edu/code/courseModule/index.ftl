[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseModuleSearchForm" action="!search" target="courseModulelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseModule.code;代码"/]
      [@b.textfields names="courseModule.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="courseModule.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseModulelist" href="!search?orderBy=courseModule.code&active=1"/]
  </div>
</div>
[@b.foot/]
