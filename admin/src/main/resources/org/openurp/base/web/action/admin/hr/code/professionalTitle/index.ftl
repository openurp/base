[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="professionalTitleSearchForm" action="!search" target="professionalTitlelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="professionalTitle.code;代码"/]
      [@b.textfields names="professionalTitle.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="professionalTitle.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="professionalTitlelist" href="!search?orderBy=professionalTitle.code&active=1"/]
  </div>
</div>
[@b.foot/]
