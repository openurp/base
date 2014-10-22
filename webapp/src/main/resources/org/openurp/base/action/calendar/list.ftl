[#ftl]
[@b.head/]
[@b.grid items=calendars var="calendar"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${calendar.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${calendar.id}"]${calendar.name}[/@][/@]
    [@b.col width="20%" property="firstDay" title="每周开始时间"]${calendar.firstDay}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${calendar.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${calendar.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
