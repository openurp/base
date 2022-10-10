[#ftl]
[@b.head/]
[@b.grid items=calendarStages var="calendarStage"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${calendarStage.id}"]${calendarStage.name}[/@][/@]
    [@b.col width="45%" property="vacation" title="是否假期"]${calendarStage.vacation?string("","")}[/@]
  [/@]
[/@]
[@b.foot/]
