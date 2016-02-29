[#ftl]
[@b.head/]
[@b.grid items=users var="user"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="学工号"]${user.code}[/@]
    [@b.col width="20%" property="name" title="姓名"][@b.a href="!info?id=${user.id}"]${user.name}[/@][/@]
    [@b.col width="20%" property="category" title="人员类别"]${(user.category.name)!}[/@]
    [@b.col width="20%" property="department" title="所在部门"]${(user.department.name)!}[/@]
  [/@]
[/@]
[@b.foot/]
