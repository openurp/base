[#ftl]
[@b.head/]
[@b.grid items=userGroups var="userGroup"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.treecol title="层级编号" width="20%"]${userGroup.indexno}[/@]
    [@b.col width="13%" property="code" title="代码"/]
    [@b.col width="30%" property="name" title="名称"/]
    [@b.col width="14%" property="enabled" title="是否启用"/]
    [@b.col property="remark" title="备注"/]
  [/@]
[/@]
[@b.foot/]
