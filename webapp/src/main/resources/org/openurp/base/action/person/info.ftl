[#ftl]
[@b.head/]
[@b.toolbar title="通用人员信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">学工号</td>
    <td class="content">${personBean.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">姓名</td>
    <td class="content">${personBean.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">性别</td>
    <td class="content">${personBean.gender.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">证件类型</td>
    <td class="content">${personBean.idType.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">证件号码</td>
    <td class="content">${personBean.sid}</td>
  </tr>
  <tr>
    <td class="title" width="20%">出生日期</td>
    <td class="content">${personBean.birthday!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">所在部门</td>
    <td class="content">${personBean.department.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">人员分类</td>
    <td class="content">${personBean.category.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">电话</td>
    <td class="content">${personBean.mobile!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">邮箱</td>
    <td class="content">${personBean.email}</td>
  </tr>
  <tr>
    <td class="title" width="20%">所在国家地区</td>
    <td class="content">${personBean.country.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">地址</td>
    <td class="content">${personBean.address!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">说明</td>
    <td class="content">${personBean.remark!}</td>
  </tr>
</table>

[@b.foot/]