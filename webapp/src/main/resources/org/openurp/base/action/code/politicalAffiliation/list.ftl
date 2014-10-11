[#ftl]
[@b.head/]
[@b.grid items=politicalAffiliations var="politicalAffiliation"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除？"));
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col width="10%" property="code" title="代码"]${politicalAffiliation.code}[/@]
    [@b.col width="10%" property="name" title="政治面貌名称"][@b.a href="!info?id=${politicalAffiliation.id}"]${politicalAffiliation.name}[/@][/@]
    [@b.col width="15%" property="enName" title="英文名称"]${politicalAffiliation.enName!}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${politicalAffiliation.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${politicalAffiliation.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]