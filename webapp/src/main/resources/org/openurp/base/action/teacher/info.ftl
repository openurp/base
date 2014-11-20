[#ftl]
[@b.head/]
[@b.toolbar title="教师信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${teacher.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${teacher.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">毕业学校</td>
    <td class="content">${teacher.school!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${teacher.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${teacher.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${teacher.remark!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">专业</td>
    <td class="content">${teacher.major.name!}</td>
  </tr>
</table>

[@b.foot/]