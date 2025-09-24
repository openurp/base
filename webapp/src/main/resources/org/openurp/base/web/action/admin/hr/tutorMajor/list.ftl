[#ftl]
[@b.head/]
[@b.grid items=tutorMajors var="tutorMajor"]
  [@b.gridbar]
    function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("staff.code:工号,staff.name:姓名,level.name:培养层次,"+
                "eduType.name:培养类型,major.name:专业,directions(name):研究方向,staff.title.name:职称",
                null,'fileName=导师研究领域'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="8%" property="staff.code" title="工号"]${tutorMajor.staff.code}[/@]
    [@b.col width="8%" property="staff.name" title="姓名"]${tutorMajor.staff.name}[/@]
    [@b.col width="8%" property="staff.title.name" title="职称"]${(staff.title.name)!}[/@]
    [@b.col width="6%" property="eduType.name" title="培养类型"/]
    [@b.col width="8%" property="level.name" title="培养层次"/]
    [@b.col width="10%" property="major.name" title="专业"/]
    [@b.col title="方向"]
      [#list tutorMajor.directions as d]${d.name}[#sep]&nbsp;[/#list]
    [/@]
  [/@]
[/@]
[@b.foot/]
