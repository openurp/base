[#ftl]
[@b.head/]
[@b.grid items=teachingGroups var="teachingGroup"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol name="teachingGroup.id"/]
    [@b.col width="10%" property="code" title="代码"]${teachingGroup.code}[/@]
    [@b.col width="35%" property="name" title="名称"/]
    [@b.col width="20%" property="department" title="院系"]${(teachingGroup.department.name)!}[/@]
    [@b.col width="20%" property="director" title="负责人"]${(teachingGroup.director.name)!}[/@]
    [@b.col width="10%" title="成员"]${(teachingGroup.members?size)!}[/@]
  [/@]
[/@]
[@b.foot/]
