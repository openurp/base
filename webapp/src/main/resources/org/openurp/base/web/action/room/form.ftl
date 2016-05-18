[#ftl]
[@b.head/]
[@b.toolbar title="修改房间信息"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if room.persisted]!update?id=${room.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="room.code" label="代码" value="${room.code!}" required="true" maxlength="10"/]
    [@b.textfield name="room.name" label="名称" value="${room.name!}" required="true" maxlength="80"/]
    [@b.select name="room.campus.id" label="所属校区" value="${(room.campus.id)!}" 
               style="width:200px;" items=campuses empty="..." required="true"/]
    [@b.select name="room.department.id" label="管理部门" value="${(room.department.id)!}" 
               style="width:200px;" items=departments empty="..."/]
    [@b.select name="room.roomType.id" label="房间类型" value="${(room.roomType.id)!}" empty="..."
               style="width:200px;" items=roomTypes /]
    [@b.select name="room.building.id" label="所属建筑" value="${(room.building.id)!}" 
               style="width:200px;"  items=buildings empty="..."/]
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