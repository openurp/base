[#ftl]
[@b.head/]
[@b.toolbar title="课程信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">代码:</td>
    <td>${course.code}</td>
    <td class="title" width="20%">名称:</td>
    <td>${course.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名:</td>
    <td>${course.enName!}</td>
    <td class="title" width="20%">培养层次:</td>
    <td>
      [#list course.levels as l]
        ${l.level.name}[#if l.credits??]<sup>${l.credits}</sup>[/#if]
        [#if l_has_next],[/#if]
      [/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">学分:</td>
    <td>${course.defaultCredits!}</td>
    <td class="title" width="20%">学时:</td>
    <td>${course.creditHours!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">周课时:</td>
    <td>${course.weekHours!}</td>
    <td class="title" width="20%">周数:</td>
    <td>${course.weeks!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">院系:</td>
    <td>${(course.department.name)!}</td>
    <td class="title" width="20%">建议课程类别:</td>
    <td>${(course.courseType.name)!}</td>
  </tr>
   <tr>
    <td class="title" width="20%">课程分类:</td>
    <td>${(course.category.name)!}</td>
    <td class="title" width="20%">考试方式:</td>
    <td>${(course.examMode.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">成绩记录方式:</td>
    <td>${course.gradingMode.name}</td>
    <td class="title" width="20%">是否计算绩点:</td>
    <td>${(course.calgp?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期:</td>
    <td >${course.beginOn!}~${course.endOn!}</td>
    <td class="title" width="20%">备注:</td>
    <td >${course.remark!}</td>
  </tr>
</table>

[@b.foot/]
