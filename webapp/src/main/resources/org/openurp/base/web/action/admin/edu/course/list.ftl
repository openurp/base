[#ftl]
[@b.head/]
[@b.grid items=courses var="course"]
  [@b.gridbar]
    function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.freeze")}",activateUser('false'),'action-freeze');
    bar.addItem("${b.text("action.activate")}",activateUser('true'),'action-activate');
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("code,name,enName,defaultCredits:学分,"+
                "creditHours,weekHours,department.name:所属院系,courseType.name:课程类型,nature.name:课程性质,"+
                "category.name:课程分类,examMode.name:考核方式,hasMakeup,calgp,beginOn,endOn",
                null,'fileName=课程信息'));
    var m = bar.addMenu("高级..");
    m.addItem("检查英文名",action.multi("checkEnName"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"/]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${course.id}"]${course.name}[/@][/@]
    [@b.col width="5%" property="defaultCredits" title="学分"/]
    [@b.col width="5%" property="creditHours" title="学时"]
      ${course.creditHours}
      [#if course.hours?size>1]
        ([#list course.hours?sort_by(['nature','code']) as ch]${ch.creditHours}[#if ch_has_next]+[/#if][/#list])
      [/#if]
    [/@]
    [@b.col width="10%" title="层次"]
      <span style="font-size:0.8em">[#list course.levels as l]${l.level.name}[#if l.credits??]<sup>${l.credits}</sup>[/#if][#sep]&nbsp;[/#list]</span>
    [/@]
    [@b.col width="5%" property="weekHours" title="周课时"/]
    [@b.col width="5%" property="weeks" title="周数"/]
    [@b.col width="15%" property="department.name" title="课程所属院系"]
      ${course.department.shortName!course.department.name}
    [/@]
    [@b.col width="15%" property="courseType.name" title="课程类型"/]
    [@b.col width="7%" property="examMode.name" title="考核方式"/]
  [/@]
[/@]
[@b.foot/]
