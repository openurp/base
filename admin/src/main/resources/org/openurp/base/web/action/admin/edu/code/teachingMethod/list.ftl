[#ftl]
[@b.head/]
[@b.grid items=teachingMethods var="teachingMethod"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${teachingMethod.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${teachingMethod.id}"]${teachingMethod.name}[/@][/@]
    [@b.col property="enName" title="英文名"]${teachingMethod.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效日期"]${teachingMethod.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效日期"]${teachingMethod.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
