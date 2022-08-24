[#ftl]
[@b.head/]
[@b.toolbar title="教师信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">工号</td>
    <td class="content">${teacher.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">姓名</td>
    <td class="content">${teacher.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content" >${teacher.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${teacher.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${teacher.remark!}</td>
  </tr>
</table>

[@b.foot/]
