[#ftl]
[@b.head/]
[@b.grid items=familyRelations var="familyRelation"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${familyRelation.code}[/@]
    [@b.col width="20%" property="name" title="人员关系名称"][@b.a href="!info?id=${familyRelation.id}"]${familyRelation.name}[/@][/@]
    [@b.col width="20%" property="enName" title="英文名称"]${familyRelation.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${familyRelation.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${familyRelation.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
