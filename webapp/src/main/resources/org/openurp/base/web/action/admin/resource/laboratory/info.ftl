[#ftl]
[@b.head/]
[@b.toolbar title="教室信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${laboratory.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${laboratory.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">房间</td>
    <td class="content">${(laboratory.room.name)!}</td>
  </tr>
</table>

[@b.foot/]
