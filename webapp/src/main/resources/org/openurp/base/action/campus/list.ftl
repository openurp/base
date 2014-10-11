[#ftl]
[@b.head/]
[@b.grid items=campuss var="campus"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${campus.code}[/@]
    [@b.col width="20%" property="name" title="校区名称"][@b.a href="!info?id=${campus.id}"]${campus.name}[/@][/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${campus.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${campus.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
