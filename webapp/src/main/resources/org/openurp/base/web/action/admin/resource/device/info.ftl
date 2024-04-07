[#ftl]
[@b.head/]
[@b.toolbar title="设备信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${device.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${device.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">教室</td>
    <td class="content">${(device.room.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">设备类型</td>
    <td class="content">${(device.deviceType.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">UUID</td>
    <td class="content">${device.uuid!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">IP</td>
    <td class="content">${device.ip!}</td>
  </tr>
</table>

[@b.foot/]
