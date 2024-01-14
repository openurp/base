[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="politicalStatusSearchForm" action="!search" target="politicalStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="politicalStatus.code;代码"/]
      [@b.textfields names="politicalStatus.name;名称"/]
      [@b.textfields names="politicalStatus.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="politicalStatus.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="politicalStatuslist" href="!search?orderBy=politicalStatus.code&active=1"/]
    </div>
  </div>
[@b.foot/]
