[#ftl]
[@b.head/]
[@b.toolbar title="课程信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="15%">代码:</td>
    <td>${course.code}</td>
    <td class="title" width="15%">名称:</td>
    <td>${course.name}</td>
    <td class="title" width="15%">培养层次:</td>
    <td>
      [#list course.levels as l]
        ${l.level.name}[#if l.credits??]<sup>${l.credits}</sup>[/#if]
        [#if l_has_next],[/#if]
      [/#list]
    </td>
  </tr>
  <tr>
    <td class="title">学分:</td>
    <td>${course.defaultCredits!}学分,${course.creditHours!}学时</td>
    <td class="title">英文名:</td>
    <td colspan="2">${course.enName!}</td>
  </tr>
  <tr>
    <td class="title">周课时:</td>
    <td>${course.weekHours!}</td>
    <td class="title">周数:</td>
    <td>${course.weeks!}</td>
    <td class="title">院系:</td>
    <td>${(course.department.name)!}</td>
  </tr>
  <tr>
    <td class="title">课程模块:</td>
    <td>${(course.module.name)!}</td>
    <td class="title">课程属性:</td>
    <td>${(course.rank.name)!}</td>
    <td class="title">课程性质:</td>
    <td>${(course.nature.name)!}</td>
  </tr>
  <tr>
    <td class="title">建议课程类别:</td>
    <td>${(course.courseType.name)!}</td>
    <td class="title">课程分类:</td>
    <td>[#list course.categories as c]${c.name}[#sep],[/#list]</td>
    <td class="title">考试方式:</td>
    <td>${(course.examMode.name)!}</td>
  </tr>
  <tr>
    <td class="title">成绩记录方式:</td>
    <td>${course.gradingMode.name}</td>
    <td class="title">是否计算绩点:</td>
    <td>${(course.calgp?string("是","否"))!}</td>
    <td class="title">是否设置补考:</td>
    <td>${(course.hasMakeup?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title">生效日期:</td>
    <td >${course.beginOn!}~${course.endOn!}</td>
    <td class="title">备注:</td>
    <td colspan="2">${course.remark!}</td>
  </tr>
</table>

[@b.foot/]
