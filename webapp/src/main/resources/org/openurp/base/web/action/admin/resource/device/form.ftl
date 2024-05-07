[#ftl]
[@b.head/]
[@b.toolbar title="修改设备"]bar.addBack();[/@]
  [@b.form action=b.rest.save(device) theme="list"]
    [@b.textfield name="device.code" label="代码" value="${device.code!}" required="true" maxlength="20"/]
    [@b.textfield name="device.name" label="名称" value="${device.name!}" required="true" maxlength="20"/]
    [@b.select name="device.deviceType.id" label="设备类型" value=device.deviceType! items=deviceTypes required="true"/]
    [@b.select name="device.room.id" label="教室" value=device.room! items=rooms required="false"/]
    [@b.textfield name="device.uuid" label="UUID" required="false"  value=device.uuid! maxlength="40"/]
    [@b.textfield name="device.ip" label="IP地址" required="false"  value=device.ip! maxlength="40"/]
    [@b.startend label="有效期"  name="device.beginOn,device.endOn" required="true,false"
      start=device.beginOn end=device.endOn format="date"/]
    [@b.textfield name="device.remark" label="备注" required="false"  value=device.remark! maxlength="40"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
    [/@]
  [/@]
[@b.foot/]