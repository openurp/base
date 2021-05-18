[#ftl]
[@b.head/]
[@b.grid items=courseAssessCategories var="courseAssessCategory"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${courseAssessCategory.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${courseAssessCategory.id}"]${courseAssessCategory.name}[/@][/@]
    [@b.col width="15%" property="enName" title="英文名"]${courseAssessCategory.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效日期"]${courseAssessCategory.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效日期"]${courseAssessCategory.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
