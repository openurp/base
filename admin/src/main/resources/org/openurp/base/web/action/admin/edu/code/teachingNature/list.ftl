[#ftl]
[@b.head/]
[@b.grid items=teachingNatures var="teachingNature"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${teachingNature.code}[/@]
    [@b.col width="15%" property="name" title="名称"][@b.a href="!info?id=${teachingNature.id}"]${teachingNature.name}[/@][/@]
    [@b.col property="enName" title="英文名"]${teachingNature.enName!}[/@]
    [@b.col width="10%" property="category" title="分类"]${teachingNature.category.title}[/@]
    [@b.col width="20%" property="beginOn" title="生效日期"]${teachingNature.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效日期"]${teachingNature.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
