[#ftl]
[@b.head/]
[@b.grid items=mentors var="mentor"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text('action.export')}",action.exportData("staff.code:工号,name:姓名,department.name:院系,staff.title.name:职称,beginOn:起始日期",null,'fileName=辅导员信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="staff.code" title="工号"]${mentor.code}[/@]
    [@b.col width="10%" property="staff.name" title="姓名"]
      [@b.a href="!info?id=${mentor.id}"]
        <span title="${mentor.beginOn!}~${mentor.endOn!}">${mentor.name}</span>
      [/@]
    [/@]
    [@b.col width="6%" property="staff.gender.name" title="性别"/]
    [@b.col property="staff.department.name" title="院系"]${(mentor.staff.department.name)!}[/@]
    [@b.col width="10%" property="staff.staffType.name" title="类别"]${(mentor.staff.staffType.name)!}[/@]
    [@b.col width="10%" property="staff.title.name" title="职称"]${(mentor.staff.title.name)!}[/@]
    [@b.col width="12%" property="staff.degree.name" title="学位"/]
    [@b.col width="10%" property="beginOn" title="任教起始"]${mentor.beginOn?string('yyyy-MM')}~${(mentor.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
