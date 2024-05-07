[#ftl]
[@b.head/]
[@b.grid items=majorDisciplines var="majorDiscipline"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="major" title="专业"]${(majorDiscipline.major.name)!}[/@]
    [@b.col width="15%" property="category" title="学科门类"]${(majorDiscipline.category.name)!}[/@]
    [@b.col width="15%" property="disciplineCode" title="学科专业代码"]${majorDiscipline.disciplineCode!}[/@]
    [@b.col width="20%" property="disciplineName" title="学科专业名称"]${majorDiscipline.disciplineName!}[/@]
    [@b.col width="15%" property="beginOn" title="有效期"]${majorDiscipline.beginOn!}~${majorDiscipline.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
