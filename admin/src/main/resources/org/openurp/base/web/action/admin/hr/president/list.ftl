[#ftl]
[@b.head/]
[@b.grid items=presidents var="president"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="20%" property="name" title="姓名"/]
    [@b.col width="20%" property="enName" title="英文名"/]
    [@b.col property="beginOn" title="任职期"]${president.beginOn?string('yyyy-MM')}~${(president.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
