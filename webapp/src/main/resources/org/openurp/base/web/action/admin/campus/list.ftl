[#ftl]
[@b.head/]
[@b.grid items=campuses var="campus"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${campus.code}[/@]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${campus.id}"]${campus.name}[/@][/@]
    [@b.col width="25%" property="enName" title="英文名称"]${campus.enName!}[/@]
    [@b.col width="10%" property="shortName" title="简称"]${campus.shortName!}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${campus.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${campus.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
