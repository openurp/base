[#ftl]
[@b.head/]
[@b.grid items=tutorMajors var="tutorMajor"]
  [@b.gridbar]
    function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="8%" property="name" title="导师"]${tutorMajor.staff.name}[/@]
    [@b.col width="6%" property="eduType.name" title="培养类型"/]
    [@b.col width="8%" property="level.name" title="培养层次"/]
    [@b.col width="10%" property="major.name" title="专业"/]
    [@b.col title="方向"]
      [#list tutorMajor.directions as d]${d.name}[#sep]&nbsp;[/#list]
    [/@]
  [/@]
[/@]
[@b.foot/]
