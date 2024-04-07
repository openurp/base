[#ftl]
[@b.head/]
[@b.grid items=teachers var="teacher"]
  [@b.gridbar]
    function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.freeze")}",activateUser('false'),'action-freeze');
    bar.addItem("${b.text("action.activate")}",activateUser('true'),'action-activate')
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text('action.export')}",action.exportData("staff.code:工号,name:姓名,department.name:院系,staff.title.name:职称,office.name:教研室,tqcNumber:教师资格证号码,oqc:其他职业资格证书和等级说明,beginOn:任教起始,endOn:任教结束,tutorType.name:导师类别,staff.status.name:在职状态",null,'fileName=教师信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="8%" property="staff.code" title="工号"]${teacher.code}[/@]
    [@b.col width="8%" property="staff.name" title="姓名"]
      [@b.a href="!info?id=${teacher.id}"]
        <span title="${teacher.beginOn!}~${teacher.endOn!}">${teacher.name}</span>
      [/@]
    [/@]
    [@b.col width="6%" property="staff.gender.name" title="性别"/]
    [@b.col property="department.name" title="院系"]${(teacher.department.name)!}[/@]
    [@b.col width="8%" property="staff.staffType.name" title="类别"]${(teacher.staff.staffType.name)!}[/@]
    [@b.col width="8%" property="staff.title.name" title="职称"]${(teacher.staff.title.name)!}[/@]
    [#if tutorTypes?size>0]
    [@b.col property="tutorType.name" width="9%" title="导师类别"/]
    [/#if]
    [@b.col width="9%" property="staff.degree.name" title="学位"/]
    [@b.col width="6%" property="staff.external" title="是否外聘"]${teacher.staff.external?string('是','否')!}[/@]
    [@b.col width="6%" property="staff.parttime" title="是否兼职"]${teacher.staff.parttime?string('是','否')!}[/@]
    [@b.col width="10%" property="beginOn" title="任教起始"]${teacher.beginOn?string('yyyy-MM')}~${(teacher.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
