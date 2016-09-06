[#ftl]
[@b.head/]
[@b.grid items=politicalStatuses var="politicalStatus"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除？"));
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col width="10%" property="code" title="代码"]${politicalStatus.code}[/@]
    [@b.col width="10%" property="name" title="名称"][@b.a href="!info?id=${politicalStatus.id}"]${politicalStatus.name}[/@][/@]
    [@b.col width="15%" property="enName" title="英文名称"]${politicalStatus.enName!}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${politicalStatus.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${politicalStatus.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]