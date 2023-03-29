[#ftl]
[@b.head/]
[@b.grid items=teachingOffices var="teachingOffice"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol name="teachingOffice.id"/]
    [@b.col width="10%" property="code" title="代码"]${teachingOffice.code}[/@]
    [@b.col property="name" title="名称"/]
    [@b.col width="20%" property="department" title="院系"]${(teachingOffice.department.name)!}[/@]
    [@b.col width="20%" property="director" title="负责人"]${(teachingOffice.director.name)!}[/@]
  [/@]
[/@]
[@b.foot/]
