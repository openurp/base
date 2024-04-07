[#ftl]
[@b.head/]
[@b.grid items=holidays var="holiday"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="name" title="假日名称"/]
    [@b.col width="40%" property="startOn" title="放假日期"/]
    [@b.col property="switchTo" title="调课至"/]
  [/@]
[/@]
[@b.foot/]
