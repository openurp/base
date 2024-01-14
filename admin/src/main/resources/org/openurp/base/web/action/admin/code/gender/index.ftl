[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="genderSearchForm" action="!search" target="genderlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="gender.code;代码"/]
      [@b.textfields names="gender.name;名称"/]
      [@b.textfields names="gender.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="gender.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="genderlist" href="!search?orderBy=gender.code&active=1"/]
    </div>
  </div>
[@b.foot/]
