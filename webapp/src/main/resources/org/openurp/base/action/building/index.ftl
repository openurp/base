[#ftl]
[@b.head/]
[@b.toolbar title="建筑类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="buildingSearchForm" action="!search" target="buildinglist" title="ui.searchForm" theme="search"]
      [@b.textfields names="building.code;代码"/]
      [@b.textfields names="building.name;名称"/]
      [@b.select name="building.campus.id" label="所属校区" href=urp.service("/base/campuses") empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="building.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="buildinglist" href="!search?orderBy=building.code"/]
    </td>
  </tr>
</table>
[@b.foot/]