[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseAbilityRateSearchForm" action="!search" target="courseAbilityRatelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseAbilityRate.code;代码"/]
      [@b.textfields names="courseAbilityRate.name;名称"/]
      <input type="hidden" name="orderBy" value="courseAbilityRate.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="courseAbilityRatelist" href="!search?orderBy=courseAbilityRate.code"/]
  </div>
</div>
[@b.foot/]
