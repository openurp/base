[#ftl]
[@b.head/]
[@b.grid  items=codeMetas var="codeMeta"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="name" title="代码名称"][@b.a href="!info?id=${codeMeta.id}"]${codeMeta.name}[/@][/@]
    [@b.col width="20%" property="title" title="中文名称"]${codeMeta.title}[/@]
    [@b.col width="20%" property="className" title="类名"]${codeMeta.className}[/@]
    [@b.col width="20%" title="所在分类"]${(codeMeta.category.name)!}[/@]
  [/@]
[/@]
[@b.foot/]
