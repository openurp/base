[#ftl]
[@b.head/]
[@b.toolbar title="学生年级信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${grade.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${grade.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名</td>
    <td class="content">${grade.enName!}</td>
  </tr>
</table>

[@b.foot/]
