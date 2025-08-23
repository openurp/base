[#ftl]
[@b.head/]
[@b.grid items=schoolYears var="schoolYear"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="name" title="名称"/]
    [@b.col width="20%" property="startYear" title="起始年份"]${schoolYear.startYear!}[/@]
    [@b.col width="20%" property="archived" title="是否归档"]${(schoolYear.archived?string("是","否"))!}[/@]
  [/@]
[/@]
[@b.foot/]
