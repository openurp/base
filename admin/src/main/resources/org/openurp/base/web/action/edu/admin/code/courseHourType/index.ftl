[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseHourTypeSearchForm" action="!search" target="courseHourTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseHourType.code;代码"/]
      [@b.textfields names="courseHourType.name;名称"/]
      <input type="hidden" name="orderBy" value="courseHourType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseHourTypelist" href="!search?orderBy=courseHourType.code"/]
  </div>
</div>
[@b.foot/]
