[#ftl]
[@b.head/]
<style>
  .info-panel-header{
    border-bottom: 1px solid #048BB3;
    color:#048BB3;
    margin-bottom:0px;
    margin-top:10px;
    padding: 0px 0px 1px 0px;
  }
  .info-panel-title{
    font-size: 0.875rem;
  }
</style>
[#macro panel title]
<h6 class="info-panel-header"><span class="info-panel-title">${title}</span></h6>
  [#nested/]
[/#macro]
<div class="container-fluid">

[@panel title="基本信息"]
  <table class="table table-sm table-detail">
    <colgroup>
      <col width="13%"/>
      <col width="20%"/>
      <col width="13%"/>
      <col width="20%"/>
      <col width="14%"/>
      <col width="20%"/>
    </colgroup>
    <tr>
      <td class="title" >工号：</td>
      <td>${staff.code}</td>
      <td class="title" >姓名：</td>
      <td>${staff.name}</td>
      <td colspan="2" rowspan="3" style="text-align:center;">[@ems.avatar username=staff.code style="border-radius: 50%;height:90px;"/]</td>
    </tr>
    <tr>
      <td class="title">性别：</td>
      <td>${staff.gender.name}</td>
      <td class="title">出生日期：</td>
      <td>${(staff.birthday?string('yyyy-MM-dd'))!}</td>
    </tr>
    <tr>
      <td class="title">民族：</td>
      <td>${(staff.nation.name)!}</td>
      <td class="title">政治面貌：</td>
      <td>${(staff.politicalStatus.name)!}</td>
    </tr>
    <tr>
      <td class="title">导师类别：</td>
      <td>[#if staff.tutorType??]${staff.tutorType.name} <span class="text-muted" title="聘任日期">${(appointOn.get(staff.tutorType)?string('yy-MM-dd'))!}</span>[/#if]</td>
      <td class="title">联系方式：</td>
      <td colspan="3">${(staff.mobile)!} ${(staff.email)!}</td>
    </tr>
  </table>
[/@]

[@panel title="工作信息"]
  <table class="table table-sm table-detail">
    <colgroup>
      <col width="13%"/>
      <col width="20%"/>
      <col width="13%"/>
      <col width="20%"/>
      <col width="14%"/>
      <col width="20%"/>
    </colgroup>
    <tr>
      <td class="title" >部门：</td>
      <td>${staff.department.name}</td>
      <td class="title" >教职工类别：</td>
      <td>${(staff.staffType.name)!} [#if staff.parttime]兼职[/#if]&nbsp;[#if staff.external]外聘[/#if]</td>
      <td class="title" >在职状态：</td>
      <td>${(staff.status.name)!}</td>
    </tr>
    <tr>
      <td class="title">是否在编：</td>
      <td>${(staff.formalHr?string('是','否'))!}</td>
      <td class="title">入校日期：</td>
      <td>${(staff.beginOn?string("yyyy-MM-dd"))!}</td>
      <td class="title">离校日期：</td>
      <td>${(staff.endOn?string("yyyy-MM-dd"))!}</td>
    </tr>
  </table>
[/@]

[@panel title="职称学历信息"]
  <table class="table table-sm table-detail">
    <colgroup>
      <col width="13%"/>
      <col width="20%"/>
      <col width="13%"/>
      <col width="20%"/>
      <col width="14%"/>
      <col width="20%"/>
    </colgroup>
    <tr>
      <td class="title" >最高学历：</td>
      <td>${(staff.educationDegree.name)!}</td>
      <td class="title">最高学位：</td>
      <td>${(staff.degree.name)!}</td>
      <td class="title">学位层次：</td>
      <td>${(staff.degreeLevel.name)!}</td>
    </tr>
    <tr>
      <td class="title">职称：</td>
      <td colspan="3">${(staff.title.name)!} <span class="text-muted">[#list staffTitles as t](${t.beginOn}~${t.endOn!})${t.title.name}[#sep],[/#list]</span></td>
      <td class="title">学位授予单位：</td>
      <td>${(staff.degreeAwardBy)!}</td>
    </tr>
  </table>
[/@]

[#if majors?size>0]
[@panel title="研究领域"]
  <table class="table table-sm table-mini">
    <colgroup>
      <col width="13%"/>
      <col width="20%"/>
      <col width="13%"/>
      <col width="20%"/>
      <col width="14%"/>
      <col width="20%"/>
    </colgroup>
  [#list majors as m]
    <tr>
      <td class="title">学科专业：</td>
      <td>${m.eduType.name} ${m.level.name} ${m.major.name}</td>
      <td class="title">研究方向：</td>
      <td colspan="3">[#list m.directions as d]${d.name}[#sep]&nbsp;[/#list]</td>
    </tr>
  [/#list]
  </table>
[/@]
[/#if]

[#if students?? && students?size>0]
<style>
  #student_table thead tr th{
    font-weight: normal;
    color:#6c757d !important;
  }
</style>
[@panel title="指导学生情况（" + students?size+"名在籍）"]
  [@b.grid items=students var="student" theme="mini" id="student_table"]
    [@b.row]
      [@b.col property="code" title="学号" width="100px"/]
      [@b.col property="name" title="姓名" width="80px"]
        <div title="${student.name}" class="text-ellipsis">${student.name}</div>
      [/@]
      [@b.col property="gender.name" title="性别" width="50px"/]
      [@b.col property="state.grade" title="年级" width="60px"/]
      [@b.col property="level.name" title="培养层次" width="70px"/]
      [@b.col property="eduType.name" title="培养类型" width="70px"/]
      [@b.col property="state.department.name" title="院系" width="70px"]
        ${student.state.department.shortName!student.state.department.name}
      [/@]
      [@b.col property="state.major.name" title="专业与方向"]
        ${(student.state.major.name)!} ${(student.state.direction.name)!}
      [/@]
    [/@]
  [/@]
[/@]
[/#if]

</div>
[@b.foot/]
