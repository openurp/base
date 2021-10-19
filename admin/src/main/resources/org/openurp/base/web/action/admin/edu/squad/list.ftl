[#ftl]
[@b.head/]
[@b.grid items=squads var="squad"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("统计人数",action.multi("statStdCount"));
    bar.addItem("${b.text("action.export")}",action.exportData("code:代码,name:名称,enName:英文名,grade:年级,level.name:培养层次,department.name:院系,major.name:专业,direction.name:方向,stdType.name:学生类别,stdCount:学生人数,planCount:计划人数,instructor.user.name:辅导员,beginOn:生效日期,endOn:失效日期",null,'fileName=班级信息'));
  [/@]
  [@b.row]
    [@b.boxcol name="squad.id"/]
    [@b.col width="9%" property="code" title="代码"]${squad.code}[/@]
    [@b.col width="23%" property="name" title="名称"][@b.a href="!info?id=${squad.id}"]${squad.name}[/@][/@]
    [@b.col width="5%" property="grade" title="年级"]${squad.grade!}[/@]
    [@b.col width="6%" property="level" title="培养层次"]${(squad.level.name)!}[/@]
    [@b.col width="15%" property="department" title="院系"]${(squad.department.name)!}[/@]
    [@b.col width="18%" property="major" title="专业(方向)"]${(squad.major.name)!} ${(squad.direction.name)!}[/@]
    [@b.col width="8%" property="stdType" title="学生类别"]${(squad.stdType.name)!}[/@]
    [@b.col width="5%" property="stdCount" title="人数"]${squad.stdCount!}[/@]
    [@b.col width="8%" property="instructor" title="辅导员"]${(squad.instructor.user.name)!}[/@]
  [/@]
[/@]
<form name="ImportExportForm" id="ImportExportForm" method="post" target="_blank"></form>

[@b.foot/]
