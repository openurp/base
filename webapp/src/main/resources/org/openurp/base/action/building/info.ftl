[#ftl]
[@b.head/]
[@b.toolbar title="建筑信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">建筑名称</td>
    <td class="content">${building.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${building.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">所属校区</td>
    <td class="content">${building.campus.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${building.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${building.endOn!}</td>
  </tr>
</table>

[@b.foot/]