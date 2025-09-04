[#ftl]
[@b.head/]
[@b.toolbar title="项目信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${project.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">适用学校</td>
    <td class="content">${project.school.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">校区列表</td>
    <td class="content">
      [#list project.campuses as campus]
        ${campus.name!}
        [#if campus_has_next],[/#if]
      [/#list]</td>
  </tr>
  <tr>
    <td class="title" width="20%">院系列表</td>
    <td class="content">
      [#list project.departments as department]
        ${department.name!}
        [#if department_has_next],[/#if]
      [/#list]</td>
  </tr>
    <tr>
    <td class="title" width="20%">培养层次列表</td>
    <td class="content">
      [#list project.levels as level]
        ${level.name!}
        [#if level_has_next],[/#if]
      [/#list]</td>
  </tr>
  <tr>
    <td class="title" width="20%">学生分类列表</td>
    <td class="content">
      [#list project.stdLabels as label]
        ${label.name!}
        [#if label_has_next],[/#if]
      [/#list]</td>
  </tr>
    <tr>
    <td class="title" width="20%">学生类别列表</td>
    <td class="content">
      [#list project.stdTypes as type]
        ${type.name!}
        [#if type_has_next],[/#if]
      [/#list]</td>
  </tr>
  <tr>
    <td class="title" width="20%">使用校历</td>
    <td class="content">
      [#list project.calendars as calendar]
        ${calendar.name!}
        [#if calendar_has_next],[/#if]
      [/#list]</td>
  </tr>
  <tr>
    <td class="title" width="20%">小节设置</td>
    <td class="content">
      [#list project.timeSettings as timeSetting]
        ${timeSetting.name!}
        [#if timeSetting_has_next],[/#if]
      [/#list]</td>
  </tr>
  <tr>
    <td class="title" width="20%">是否辅修</td>
    <td class="content">${(project.minor?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">描述</td>
    <td class="content">${project.description!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content" >${project.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${project.endOn!}</td>
  </tr>

</table>

[@b.foot/]
