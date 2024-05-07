[#ftl]
[@b.head/]
[@b.toolbar title="教职工信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
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
</style>
[#macro panel title]
<h6 class="panel-header"><span class="panel_title">${title}</span></h6>
  [#nested/]
[/#macro]
<div class="container-fluid text-sm">

[@panel title="基本信息"]
  <table class="infoTable">
    <tr>
      <td class="title" width="120px">工号：</td>
      <td width="20%">${staff.code}</td>
      <td class="title" width="120px">姓名：</td>
      <td width="20%">${staff.name}</td>
      <td width="120px"></td>
      <td width="20%"></td>
    </tr>
    <tr>
      <td class="title">性别：</td>
      <td>${staff.gender.name}</td>
      <td class="title">民族：</td>
      <td>${(staff.nation.name)!}</td>
      <td class="title">政治面貌：</td>
      <td>${(staff.politicalStatus.name)!}</td>
    </tr>
    <tr>
      <td class="title">出生日期：</td>
      <td>${(staff.birthday?string('yyyy-MM-dd'))!}</td>
      <td class="title">证件类型：</td>
      <td>${(staff.idType.name)!}</td>
      <td class="title">证件号码：</td>
      <td>${(staff.idNumber)!}</td>
    </tr>
  </table>
[/@]

[@panel title="工作信息"]
  <table class="infoTable">
    <tr>
      <td class="title" width="120px">部门：</td>
      <td width="20%">${staff.department.name}</td>
      <td class="title" width="120px">教职工类别：</td>
      <td width="20%">${(staff.staffType.name)!}</td>
      <td class="title" width="120px">在职状态：</td>
      <td width="20%">${(staff.status.name)!}</td>
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
  <table class="infoTable">
    <tr>
      <td class="title" width="120px">职称：</td>
      <td width="20%">${(staff.title.name)!}</td>
      <td class="title" width="120px">学历：</td>
      <td width="20%">${(staff.educationDegree.name)!}</td>
      <td class="title" width="120px">学位：</td>
      <td width="20%">${(staff.degree.name)!}</td>
    </tr>
    <tr>
      <td class="title">学位层次：</td>
      <td>${(staff.degreeLevel.name)!}</td>
      <td class="title">学位授予单位:</td>
      <td colspan="3">${(staff.degreeAwardBy)!}</td>
    </tr>
  </table>
[/@]

[@panel title="联系信息"]
  <table class="infoTable">
    <tr>
      <td class="title" width="120px">手机：</td>
      <td width="20%">${(staff.mobile)!}</td>
      <td class="title" width="120px">电子邮件：</td>
      <td width="20%">${(staff.email)!}</td>
      <td class="title" width="120px">个人主页：</td>
      <td width="20%">${(staff.homepage)!}</td>
    </tr>
    <tr>
      <td class="title">是否兼职、外聘：</td>
      <td>[#if staff.parttime]兼职[/#if]&nbsp;[#if staff.external]外聘[/#if]</td>
      <td class="title">全职工作单位:</td>
      <td colspan="3">${(staff.organization)!}</td>
    </tr>
  </table>
[/@]
</div>
[@b.foot/]
