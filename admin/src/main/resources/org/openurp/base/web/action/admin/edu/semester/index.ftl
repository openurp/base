[#ftl]
[@b.head/]
[@b.nav class="nav-tabs nav-tabs-compact"]
  [@b.navitem href="semester"]学年学期[/@]
  [@b.navitem href="calendar"]日历方案[/@]
  [@b.navitem href="calendar-stage"]学期阶段安排[/@]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="semesterSearchForm" action="!search" target="semesterlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="semester.code;代码"/]
      [@b.textfields names="semester.name;名称"/]
      <input type="hidden" name="orderBy" value="semester.code desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="semesterlist" href="!search?orderBy=semester.code desc"/]
  </div>
</div>
[@b.foot/]
