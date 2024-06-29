[#ftl]
[@b.head/]
[@b.grid items=directors var="director"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="director.staff.code" title="职工号"/]
    [@b.col width="15%" property="director.name" title="姓名"/]
    [@b.col width="15%" property="director.department.name" title="院系"/]
    [@b.col property="major.name" title="专业名称"]${(director.major.code)!} ${(director.major.name)}[/@]
    [@b.col property="direction" title="专业方向"]${(director.direction.code)!} ${(director.direction.name)}[/@]
    [@b.col width="15%" property="beginOn" title="开始于"]${(major.beginOn?string('yyyy-MM'))!}[/@]
  [/@]
[/@]
[@b.foot/]
