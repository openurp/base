[#ftl]
[@b.head/]
[@b.grid items=rooms var="room"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${room.code}[/@]
    [@b.col width="15%" property="name" title="名称"][@b.a href="!info?id=${room.id}"]${room.name}[/@][/@]
    [@b.col width="15%" property="campus" title="所属校区"]${room.campus.name}[/@]
    [@b.col width="10%" property="building" title="所属建筑"]${(room.building.name)!}[/@]
    [@b.col width="10%" property="floor" title="楼层"]${room.floor!}[/@]
    [@b.col width="10%" property="capacity" title="容量"]${room.capacity!}[/@]
    [@b.col width="10%" property="beginOn" title="生效时间"]${room.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效时间"]${room.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
