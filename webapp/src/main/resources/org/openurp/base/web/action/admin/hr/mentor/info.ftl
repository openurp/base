[#ftl]
[@b.head/]
[@b.toolbar title="辅导员信息"]
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

[#assign staff=mentor.staff/]
[@panel title="基本信息"]
  <table class="table table-sm table-detail">
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

[@panel title="联系信息"]
  <table class="table table-sm table-detail">
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

[@panel title="带班信息"]
  [@b.grid items=squads?sort_by('beginOn')?reverse var="squad" theme="mini"]
    [@b.row]
      [@b.col width="9%" property="code" title="代码"]${squad.code}[/@]
      [@b.col width="23%" property="name" title="名称"]<div class="text-ellipsis" title="${squad.name}">${squad.name}</div>[/@]
      [@b.col width="5%" property="grade" title="年级"]${squad.grade!}[/@]
      [@b.col width="6%" property="level" title="培养层次"]${(squad.level.name)!}[/@]
      [@b.col width="12%" property="department" title="院系"]${squad.department.shortName!squad.department.name}[/@]
      [@b.col width="18%" property="major" title="专业(方向)"]${(squad.major.name)!} ${(squad.direction.name)!}[/@]
      [@b.col width="8%" property="stdType" title="学生类别"]${(squad.stdType.name)!}[/@]
      [@b.col width="5%" property="stdCount" title="人数"]${squad.stdCount!}[/@]
    [/@]
  [/@]
[/@]
</div>
[@b.foot/]
