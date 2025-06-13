[#ftl]
[@b.head/]
[@b.grid items=grades var="grade"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${grade.code}[/@]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${grade.id}"]${grade.name}[/@][/@]
    [@b.col width="40%" property="enName" title="英文名"]${grade.enName!}[/@]
    [@b.col property="beginIn" title="入学毕业年月"]${grade.beginIn?string("yyyy-MM")}~${grade.endIn?string("yyyy-MM")}[/@]
  [/@]
[/@]
[@b.foot/]
