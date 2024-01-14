[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="professionalGradeSearchForm" action="!search" target="professionalGradelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="professionalGrade.code;代码"/]
      [@b.textfields names="professionalGrade.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="professionalGrade.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="professionalGradelist" href="!search?orderBy=professionalGrade.code&active=1"/]
  </div>
</div>
[@b.foot/]
