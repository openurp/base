[#ftl]
[@b.head/]
[@b.toolbar title="教学日历方案"]
  bar.addItem("学期阶段","toStage()")
  function toStage(){
     bg.Go("${base}/calendar-stage","_blank");
  }
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
