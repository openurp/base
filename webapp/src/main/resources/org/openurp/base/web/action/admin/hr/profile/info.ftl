[@b.head/]
[@b.toolbar title="教师简介"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable" width="100%">
  <tr>
    <td class="title" width="15%">工号:</td>
    <td class="content">${(profile.staff.code)!}</td>
    <td class="title">姓名:</td>
    <td class="content"> ${(profile.staff.name)!}</td>
  </tr>
  <tr>
    <td class="title">部门:</td>
    <td class="content">${(profile.staff.department.name)!}</td>
    <td class="title">职称:</td>
    <td class="content">${(profile.staff.title.name)!}</td>
  </tr>
  <tr>
    <td class="title">学历:</td>
    <td class="content">${(profile.staff.educationDegree.name)!}</td>
    <td class="title">学位:</td>
    <td class="content">${(profile.staff.degree.name)!}</td>
  </tr>
</table>
[#include "site.ftl"/]
[@b.foot/]
