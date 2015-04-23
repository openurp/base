[#ftl]
[@b.head/]
[@b.toolbar title="房间类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="roomSearchForm" action="!search" target="roomlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="room.code;代码"/]
      [@b.textfields names="room.name;名称"/]
      [@b.select name="room.campus.id" label="所属校区" href=urp.service("/base/campuses") empty="..." style="width:100px"/]
      [@b.select name="room.department.id" label="管理部门" href=urp.service("/base/departments") empty="..." style="width:100px"/]
      [@b.select name="room.roomType.id" label="房间类型" empty="..." href=urp.service("/base/code/room-types") style="width:100px"/]
      [@b.select name="room.building.id" label="所属建筑" href=urp.service("/base/buildings") empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="room.code"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="roomlist" href="!search?orderBy=room.code"/]
    </td>
  </tr>
</table>
[@b.foot/]