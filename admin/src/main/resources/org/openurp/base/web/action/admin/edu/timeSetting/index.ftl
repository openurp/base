[#ftl]
[@b.head/]
  [@b.nav class="nav-tabs nav-tabs-compact"]
    [@b.navitem href="course-unit"]节次设置[/@]
    [@b.navitem href="time-setting"]作息时间方案[/@]
  [/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="timeSettingSearchForm" action="!search" target="timeSettinglist" title="ui.searchForm" theme="search"]
      [@b.textfields names="timeSetting.name;名称"/]
      <input type="hidden" name="orderBy" value="name"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="timeSettinglist" href="!search?orderBy=id"/]
  </div>
</div>
[@b.foot/]
