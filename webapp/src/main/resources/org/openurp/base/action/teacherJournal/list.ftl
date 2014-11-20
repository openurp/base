[#ftl]
[@b.head/]
[@b.grid items=teacherJournals var="teacherJournal"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="id" title="id"]${teacherJournal.id}[/@]
    [@b.col width="20%" property="teacher" title="教师"]${teacherJournal.teacher.name!}[/@]
    [@b.col width="15%" property="department" title="部门"]${teacherJournal.department.name!}[/@]
    [@b.col width="10%" property="beginOn" title="生效时间"]${teacherJournal.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效时间"]${teacherJournal.endOn!}[/@]
  [/@]
  [/@]
[@b.foot/]
