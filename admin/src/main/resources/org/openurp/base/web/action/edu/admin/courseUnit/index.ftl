[#ftl]
[@b.head/]
  [@b.nav class="nav-tabs nav-tabs-compact"]
    [@b.navitem href="course-unit"]节次设置[/@]
    [@b.navitem href="time-setting"]作息时间方案[/@]
  [/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="courseUnitSearchForm" action="!search" target="courseUnitlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseUnit.name;名称"/]
      [@b.textfields names="courseUnit.indexno;序号"/]
      [@b.select name="courseUnit.setting.id" label="方案" items=settings/]
      <input type="hidden" name="orderBy" value="courseUnit.indexno"/>
    [/@]
    </div>
    <div class="search-list">
    [#if settings.size>0]
      [@b.div id="courseUnitlist" href="!search?orderBy=courseUnit.indexno&courseUnit.setting.id="+settings?first.id/]
    [#else]
      没有作息方案，请先制定作息方案。
    [/#if]
  </div>
</div>
[@b.foot/]
