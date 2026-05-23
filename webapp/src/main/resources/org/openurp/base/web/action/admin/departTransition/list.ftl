[#ftl]
[@b.head/]
[@b.grid items=transitions var="transition" sortable="false"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="20%" title="原部门" property="from.name" /]
    [@b.col width="20%" title="目标部门" property="to.name"/]
    [@b.col width="15%" property="effectiveOn" title="生效日期"/]
    [@b.col property="remark" title="备注"][/@]
  [/@]
[/@]
[@b.foot/]
