[#ftl]
[@b.head/]
[@b.grid items=buildings var="building"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${building.code}[/@]
    [@b.col width="20%" property="name" title="建筑名称"][@b.a href="!info?id=${building.id}"]${building.name}[/@][/@]
    [@b.col width="20%" property="campus" title="所属校区"]${building.campus.name}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${building.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${building.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
