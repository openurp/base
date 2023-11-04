[#ftl]
[@b.head/]
[@b.grid items=gradeTypes var="gradeType"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${gradeType.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${gradeType.id}"]${gradeType.name}[/@][/@]
    [@b.col property="enName" title="英文名"]${gradeType.enName!}[/@]
    [@b.col property="examType.name" title="考试类型"]${(gradeType.examType.name)!}[/@]
    [@b.col width="20%" property="beginOn" title="生效日期"]${gradeType.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效日期"]${gradeType.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
