[#ftl]
[@b.head/]
[@b.grid items=devices var="device"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("code:代码,name:名称,room.name:教室,deviceType.name:设备类型,room.campus.name:校区,uuid:UUID,ip:IP,beginOn:生效日期,endOn:失效日期",null,'fileName=设备信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${device.code}[/@]
    [@b.col property="name" title="名称"]
       [@b.a href="!info?id=${device.id}"]${device.name} [/@]
    [/@]

    [@b.col width="10%" property="deviceType.name" title="教室类型"/]
    [@b.col width="15%" property="room.name" title="教室"/]
    [@b.col width="10%" property="room.campus.name" title="校区"]
      ${(device.room.campus.shortName)!((device.room.campus.name)!'--')}
    [/@]
    [@b.col width="25%" property="uuid" title="UUID"/]
    [@b.col width="12%" property="ip" title="IP"/]
  [/@]
  [/@]
[@b.foot/]
