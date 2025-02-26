[#ftl]
[@b.head/]
[@b.grid items=users var="user"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("${b.text('action.export')}",action.exportData("code:账号,name:姓名,category.name:人员类别,department.name:部门,mobile:移动电话,email:电子邮箱",null,'fileName=用户信息'));
    bar.addItem("初始化",action.method('initAccount'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="13%" property="code" title="账号"]${user.code}[/@]
    [@b.col width="14%" property="name" title="姓名"][@b.a href="!info?id=${user.id}"]${user.name}[/@][/@]
    [@b.col width="8%" property="category" title="人员类别"]${(user.category.name)!}[/@]
    [@b.col width="8%" property="group" title="用户组"]${(user.group.name)!}[/@]
    [@b.col width="16%" property="department" title="所在部门"]${(user.department.name)!}[/@]
    [@b.col width="10%" property="mobile" title="移动电话"]
      [#if user.mobile?? && user.mobile?length>10]
        <span title="${user.mobile}">${(user.mobile[0..2])!}****${(user.mobile[7..10])!}</span>
      [#else]
        ${(user.mobile)!"--"}
      [/#if]
    [/@]
    [@b.col property="email" title="电子邮箱"/]
    [@b.col width="13%" property="endOn" title="有效期限"]${user.beginOn?string('yy-MM')}~${(user.endOn?string('yy-MM-dd'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
