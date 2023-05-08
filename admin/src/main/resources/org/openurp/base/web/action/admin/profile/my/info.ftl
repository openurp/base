[#ftl/]
[@b.head/]
[@b.toolbar title="教职工简介"/]
<div class="container">
  <div style="text-align:center">
    [@b.a class="btn btn-sm btn-outline-primary" href="!edit?id=" +profile.id role="button"]<i class="fa fa-edit"></i>修改[/@]
  </div>
  <table class="infoTable" width="100%" style="box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);">
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
  [#include "../staff/site.ftl"/]
</div>
[@b.foot/]
