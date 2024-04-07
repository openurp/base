[#ftl]
[@b.head/]
[@b.grid items=secretaries var="secretary"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="staff.code" title="工号"]${secretary.staff.code}[/@]
    [@b.col width="10%" property="staff.name" title="姓名"]
      [@b.a href="!info?id=${secretary.id}"]
        <span title="${secretary.beginOn!}~${secretary.endOn!}">${secretary.staff.name}</span>
      [/@]
    [/@]
    [@b.col width="6%" property="staff.gender.name" title="性别"/]
    [@b.col property="staff.department.name" title="院系"]${(secretary.staff.department.name)!}[/@]
    [@b.col property="officePhone" title="办公电话"/]
    [@b.col property="officeAddr" title="办公地址"/]
    [@b.col width="10%" property="beginOn" title="任教起始"]${secretary.beginOn?string('yyyy-MM')}~${(secretary.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
