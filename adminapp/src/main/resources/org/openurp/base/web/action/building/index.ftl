[#ftl]
[@b.head/]
[@b.toolbar title="建筑信息"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="buildingSearchForm" action="!search" target="buildinglist" title="ui.searchForm" theme="search"]
      [@b.textfields names="building.code;代码"/]
      [@b.textfields names="building.name;名称"/]
      [@b.select name="building.campus.id" label="所属校区" items=campuses empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="building.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="buildinglist" href="!search?orderBy=building.code"/]
    </div>
  </div>
[@b.foot/]
