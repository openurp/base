[#ftl]
[@b.head/]
[@b.toolbar title="新建房间类型"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]
    [@b.textfield name="room.code" label="代码" value="${room.code!}" required="true" maxlength="10"/]
    [@b.textfield name="room.name" label="名称" value="${room.name!}" required="true" maxlength="80"/]
    [@b.select name="room.campus.id" label="所属校区" value="${room.campus!}" 
               style="width:200px;" href=urp.service("/base/campuses") empty="..." required="true"/]
    [@b.select name="room.department.id" label="管理部门" value="${room.department!}" 
               style="width:200px;" href=urp.service("/base/departments") empty="..."/]
    [@b.select name="room.roomType.id" label="房间类型" value="${room.roomType!}" empty="..."
               style="width:200px;"  href=urp.service("/base/code/room-types")/]
    [@b.select name="room.building.id" label="所属建筑" value="${room.building!}" 
               style="width:200px;" href=urp.service("/base/buildings") empty="..."/]
    [@b.textfield name="room.floor" label="楼层" value="${room.floor!}" required="true" maxlength="10"/]
    [@b.textfield name="room.capacity" label="容量" value="${room.capacity!}" required="true" maxlength="10"/]
    [@b.startend label="生效失效时间" 
      name="room.beginOn,room.endOn" required="false,false" 
      start=room.beginOn end=room.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]