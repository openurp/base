[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="pressGradeSearchForm" action="!search" target="pressGradelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="pressGrade.code;代码"/]
      [@b.textfields names="pressGrade.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="pressGrade.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="pressGradelist" href="!search?orderBy=pressGrade.code&active=1"/]
  </div>
</div>
[@b.foot/]
