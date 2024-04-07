[#ftl]
[@b.head/]
[@b.grid items=examTypes var="examType"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${examType.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${examType.id}"]${examType.name}[/@][/@]
    [@b.col property="enName" title="英文名"]${examType.enName!}[/@]
    [@b.col property="forDeferred" title="是否是缓考"]${examType.forDeferred?string("是","否")}[/@]
    [@b.col width="15%" property="beginOn" title="生效日期"]${examType.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效日期"]${examType.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
