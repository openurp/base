[#ftl]
[@b.head/]
[@b.grid items=stdTypes var="stdType"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${stdType.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${stdType.id}"]${stdType.name}[/@][/@]
    [@b.col property="enName" title="英文名"]${stdType.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效日期"]${stdType.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效日期"]${stdType.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
