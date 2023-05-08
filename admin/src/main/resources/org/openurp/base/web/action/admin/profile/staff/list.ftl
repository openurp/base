[#ftl]
[@b.head/]
[@b.grid items=profiles var="profile"]
  [@b.gridbar]
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("新增",action.method("missings"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="staff.code" title="工号"/]
    [@b.col width="10%" property="staff.name" title="姓名"][@b.a href="!info?id=${profile.id}"]${profile.staff.name}[/@][/@]
    [@b.col width="18%" property="staff.department.name" title="部门"/]
    [@b.col width="17%" property="staff.title.name" title="职称"/]
    [@b.col width="20%" property="staff.degree.name" title="学位"/]
    [@b.col width="20%" property="staff.educationDegree.name" title="学历"/]
  [/@]
[/@]
[@b.foot/]
