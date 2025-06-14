[#ftl]
[@b.head/]
<style>
  .panel-header{
    border-bottom: 1px solid #048BB3;
    color:#048BB3;
    margin-bottom:0px;
    margin-top:10px;
    padding: 0px 0px 1px 0px;
  }
  .panel_title{
    font-size: 0.875rem;
  }
  table td.title {
    padding: 0.2rem 0rem;
    text-align:right;
    color: #6c757d !important;
  }
  table-fixed {
    table-layout:fixed;
  }
</style>
[#macro panel title]
<h6 class="panel-header"><span class="panel_title">${title}</span></h6>
  [#nested/]
[/#macro]
<div class="container-fluid">

[@panel title="基本信息"]
  <table class="table table-sm table-fixed">
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
      <td colspan="2" rowspan="3" style="text-align:center;">[@ems.avatar username=staff.code style="border-radius: 50%;width:80px;"/]</td>
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
      <td>[#if staff.tutorType??]${staff.tutorType.name} <span class="text-muted" title="聘任日期">${(appointOn.get(staff.tutorType)?string('yyyy-MM-dd'))!}</span>[/#if]</td>
      <td class="title">联系方式：</td>
      <td colspan="3">${(staff.mobile)!} ${(staff.email)!}</td>
    </tr>
  </table>
[/@]

[@panel title="工作信息"]
  <table class="table table-sm table-fixed">
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
  <table class="table table-sm table-fixed">
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
  <table class="table table-sm table-fixed">
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

</div>
[@b.foot/]
