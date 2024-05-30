[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
      [@b.form name="courseSearchForm" action="!search" target="courselist" title="ui.searchForm" theme="search"]
        [@b.textfield name="courseTextbook.course.code" label="代码" maxlength="5000"/]
        [@b.textfield name="courseTextbook.course.name" label="名称"/]
        [@base.code type="course-natures" name="courseTextbook.course.nature.id" label="课程性质" empty="..." /]
        [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1" /]
        <input type="hidden" name="orderBy" value="courseTextbook.course.code"/>
      [/@]
    </div>
    <div class="search-list">[@b.div id="courselist" href="!search?orderBy=courseTextbook.course.code asc&active=1"/]
  </div>
</div>
[@b.foot/]
