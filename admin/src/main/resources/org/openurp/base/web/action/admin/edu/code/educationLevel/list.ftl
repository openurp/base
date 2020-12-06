[#ftl]
[@b.head/]
[@b.grid items=educationLevels var="educationLevel"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${educationLevel.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${educationLevel.id}"]${educationLevel.name}[/@][/@]
    [@b.col width="15%" property="enName" title="英文名"]${educationLevel.enName!}[/@]
    [@b.col width="20%" title="层次"]${educationLevel.fromLevel.name} ～ ${educationLevel.toLevel.name}[/@]
    [@b.col width="20%" property="beginOn" title="生效日期"]${educationLevel.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效日期"]${educationLevel.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
