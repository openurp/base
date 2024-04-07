[#ftl]
[@b.head/]
[@b.toolbar title="房间信息"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="roomSearchForm" action="!search" target="roomlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="room.code" label="代码" maxlength="5000"/]
      [@b.textfields names="room.name;名称"/]
      [@b.select name="room.campus.id" label="所属校区" items=campuses empty="..." /]
      [@b.select name="room.roomType.id" label="房间类型" empty="..." items=roomTypes /]
      [@b.select name="room.building.id" label="教学楼" empty="..." items=buildings /]
      [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="room.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="roomlist" href="!search?orderBy=room.code&active=1"/]
    </div>
  </div>
[@b.foot/]
