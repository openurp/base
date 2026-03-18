[#ftl]
[@b.head/]
[@b.grid items=assignments var="assignment"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="staff.code" title="工号"]${assignment.staff.code}[/@]
    [@b.col width="10%" property="staff.name" title="姓名"]
      [@b.a href="!info?id=${assignment.id}"]
        <span title="${assignment.beginOn!}~${assignment.endOn!}">${assignment.staff.name}</span>
      [/@]
    [/@]
    [@b.col width="6%" property="staff.gender.name" title="性别"/]
    [@b.col property="department.name" title="部门"]${(assignment.department.name)!}[/@]
    [@b.col property="rank.name" title="职务级别" width="7%"]${(assignment.rank.name)!}[/@]
    [@b.col property="post" title="职务"/]
    [@b.col property="concurrent" title="是否兼任" width="7%"]
      ${(assignment.concurrent)?string("是","--")}
    [/@]
    [@b.col width="10%" property="beginOn" title="起始"]${assignment.beginOn?string('yyyy-MM')}~${(assignment.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
