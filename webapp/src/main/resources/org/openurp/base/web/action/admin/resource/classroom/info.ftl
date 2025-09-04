[#ftl]
[@b.head/]
[@b.toolbar title="教室信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${classroom.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${classroom.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名</td>
    <td class="content">${classroom.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">房间</td>
    <td class="content">${(classroom.room.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">教室类型</td>
    <td class="content">${(classroom.roomType.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">上课容量</td>
    <td class="content">${classroom.courseCapacity!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">考试容量</td>
    <td class="content">${classroom.examCapacity!}</td>
  </tr>
</table>

[@b.foot/]
