[#ftl]
[@b.head/]
[@b.nav class="nav-tabs nav-tabs-compact"]
  [@b.navitem href="semester"]学年学期[/@]
  [@b.navitem href="calendar"]日历方案[/@]
  [@b.navitem href="calendar-stage"]学期阶段安排[/@]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="calendarSearchForm" action="!search" target="calendarlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="calendar.code;代码"/]
      [@b.textfields names="calendar.name;名称"/]
      <input type="hidden" name="orderBy" value="code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="calendarlist" href="!search?orderBy=code"/]
  </div>
</div>
[@b.foot/]
