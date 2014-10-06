[#ftl]
[@b.head/]
[@b.grid items=personBeans var="personBean"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="学工号"][@b.a href="!info?id=${personBean.id}"]${personBean.code}[/@][/@]
    [@b.col width="20%" property="name" title="姓名"]${personBean.name}[/@]
    [@b.col width="20%" property="gender" title="性别"]${personBean.gender.name!}[/@]
    [@b.col width="30%" property="department" title="所在部门"]${personBean.department.name!}[/@]
  [/@]
[/@]
[@b.foot/]
