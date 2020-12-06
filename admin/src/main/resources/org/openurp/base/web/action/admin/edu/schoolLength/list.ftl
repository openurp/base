[#ftl]
[@b.head/]
[@b.grid items=schoolLengths var="schoolLength"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="level.name" title="培养层次"]${(schoolLength.level.name)!}[/@]
    [@b.col width="30%" property="fromGrade" title="年级范围"]${schoolLength.fromGrade}~${schoolLength.toGrade!}[/@]
    [@b.col width="15%" property="normal" title="学制"/]
    [@b.col width="35%" property="minimum" title="学习年限"]${schoolLength.minimum}~${schoolLength.maximum}年[/@]
  [/@]
[/@]
[@b.foot/]
