[#ftl]
[@b.head/]
[@b.nav class="nav-tabs nav-tabs-compact"]
  [@b.navitem href="semester"]学年学期[/@]
  [@b.navitem href="calendar"]日历方案[/@]
  [@b.navitem href="calendar-stage"]学期阶段安排[/@]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stageSearchForm" action="!search" target="stagelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="calendarStage.name;名称"/]
      <input type="hidden" name="orderBy" value="calendarStage.name"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stagelist" href="!search?orderBy=calendarStage.id"/]
  </div>
</div>
[@b.foot/]
