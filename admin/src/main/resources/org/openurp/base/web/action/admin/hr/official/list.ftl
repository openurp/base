[#ftl]
[@b.head/]
[@b.grid items=officials var="official"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="staff.code" title="工号"]${official.staff.code}[/@]
    [@b.col width="10%" property="staff.name" title="姓名"]
      [@b.a href="!info?id=${official.id}"]
        <span title="${official.beginOn!}~${official.endOn!}">${official.staff.name}</span>
      [/@]
    [/@]
    [@b.col width="6%" property="staff.gender.name" title="性别"/]
    [@b.col property="department.name" title="部门"]${(official.department.name)!}[/@]
    [@b.col property="duty" title="职务"/]
    [@b.col property="parttime" title="是否兼任" width="7%"]
      ${(official.parttime)?string("是","否")}
    [/@]
    [@b.col width="10%" property="beginOn" title="起始"]${official.beginOn?string('yyyy-MM')}~${(official.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
