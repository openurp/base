[#ftl]
[@b.head/]
[@b.grid  items=codeCategorys var="codeCategory"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="name" title="代码类名"][@b.a href="!info?id=${codeCategory.id}"]${codeCategory.name}[/@][/@]
    [@b.col width="20%" property="indexno" title="序号"]${codeCategory.indexno}[/@]
    [@b.col width="20%" title="父级菜单"]${(codeCategory.parent.name)!}[/@]
  [/@]
[/@]
[@b.foot/]
