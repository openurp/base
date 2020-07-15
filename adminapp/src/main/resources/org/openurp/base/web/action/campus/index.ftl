[#ftl]
[@b.head/]
[@b.toolbar title="校区类型"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="campusSearchForm" action="!search" target="campuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="campus.code;代码"/]
      [@b.textfields names="campus.name;名称"/]
      <input type="hidden" name="orderBy" value="campus.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="campuslist" href="!search?orderBy=campus.code"/]
    </div>
  </div>
[@b.foot/]
