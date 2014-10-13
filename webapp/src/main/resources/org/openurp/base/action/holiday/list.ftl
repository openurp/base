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
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${holiday.id}"]${holiday.name}[/@][/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${holiday.beginOn}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${holiday.endOn}[/@]
  [/@]
[/@]
[@b.foot/]
