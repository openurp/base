[#ftl]
[@b.head/]
[@b.grid items=squads var="squad"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    var m=bar.addMenu("统计人数",action.multi("statStdCount"));
    m.addItem("学生名单维护",action.single("assign"));
    m.addItem("自动指定学生",action.multi("autoAssign"));
    bar.addItem("单个复制",action.single("copy"));
    bar.addItem("${b.text("action.export")}",action.exportData("code:代码,name:名称,enName:英文名,grade.name:年级,level.name:培养层次,department.name:院系,major.name:专业,direction.name:方向,stdType.name:学生类别,stdCount:学生人数,planCount:计划人数,mentor.code:辅导员工号,mentor.name:辅导员姓名,master.name:班主任,beginOn:生效日期,endOn:失效日期",null,'fileName=班级信息'));
  [/@]
  [@b.row]
    [@b.boxcol name="squad.id"/]
    [@b.col width="9%" property="code" title="代码"]${squad.code}[/@]
    [@b.col width="23%" property="name" title="名称"][@b.a href="!info?id=${squad.id}"]<div class="text-ellipsis" title="${squad.name}">${squad.name}</div>[/@][/@]
    [@b.col width="5%" property="grade" title="年级"]${squad.grade!}[/@]
    [@b.col width="6%" property="level" title="培养层次"]${(squad.level.name)!}[/@]
    [@b.col width="12%" property="department" title="院系"]${squad.department.shortName!squad.department.name}[/@]
    [@b.col width="18%" property="major" title="专业(方向)"]${(squad.major.name)!} ${(squad.direction.name)!}[/@]
    [@b.col width="8%" property="stdType" title="学生类别"]${(squad.stdType.name)!}[/@]
    [@b.col width="5%" property="stdCount" title="人数"]${squad.stdCount!}[/@]
    [@b.col property="mentor" title="管理人员"]
      <span style="font-size:0.8em">
      [#if squad.mentor??]${squad.mentor.name}<sup>辅</sup>[/#if]
      [#if squad.master??]${squad.master.name}<sup>班</sup>[/#if]
      </span>
    [/@]
  [/@]
[/@]
[@b.foot/]
