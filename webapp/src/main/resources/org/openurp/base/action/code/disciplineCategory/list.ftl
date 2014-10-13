[#ftl]
[@b.head/]
[@b.grid items=disciplineCategorys var="disciplineCategory"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${disciplineCategory.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${disciplineCategory.id}"]${disciplineCategory.name}[/@][/@]
    [@b.col width="20%" property="enName" title="英文名称"]${disciplineCategory.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${disciplineCategory.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${disciplineCategory.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
