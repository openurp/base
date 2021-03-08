[#ftl]
[@b.head/]
[@b.toolbar title="房间信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${room.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${room.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">所属校区</td>
    <td class="content">${room.campus.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">管理部门</td>
    <td class="content">${(room.department.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">房间类型</td>
    <td class="content">${(room.roomType.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">所属建筑</td>
    <td class="content">${(room.building.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">楼层</td>
    <td class="content">${room.floorNo}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${room.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${room.endOn!}</td>
  </tr>
</table>

[@b.foot/]
