[#ftl]
[@b.head/]
[@b.toolbar title="课程信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码:</td>
    <td class="content">${course.code}</td>
    <td class="title" width="20%">名称:</td>
    <td class="content">${course.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名:</td>
    <td class="content">${course.enName!}</td>
    <td class="title" width="20%">培养层次:</td>
    <td class="content">
      [#list course.levels as l]
        ${l.level.name}[#if l.credits??]<sup>${l.credits}</sup>[/#if]
        [#if l_has_next],[/#if]
      [/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">学分:</td>
    <td class="content">${course.defaultCredits!}</td>
    <td class="title" width="20%">学时:</td>
    <td class="content">${course.creditHours!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">周课时:</td>
    <td class="content">${course.weekHours!}</td>
    <td class="title" width="20%">周数:</td>
    <td class="content">${course.weeks!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">院系:</td>
    <td class="content">${(course.department.name)!}</td>
    <td class="title" width="20%">建议课程类别:</td>
    <td class="content">${(course.courseType.name)!}</td>
  </tr>
   <tr>
    <td class="title" width="20%">评教分类:</td>
    <td class="content">${(course.category.name)!}</td>
    <td class="title" width="20%">考试方式:</td>
    <td class="content">${(course.examMode.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">成绩记录方式:</td>
    <td class="content">${(course.markStyle.name)!}</td>
    <td class="title" width="20%">是否计算绩点:</td>
    <td class="content">${(course.calgp?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">先修课程:</td>
    <td class="content" colspan="3">
    [#list course.prerequisites as p]
      ${p.code} ${p.name}
    [/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期:</td>
    <td class="content" >${course.beginOn!}~${course.endOn!}</td>
    <td class="title" width="20%">备注:</td>
    <td class="content" >${course.remark!}</td>
  </tr>
</table>

[@b.foot/]
