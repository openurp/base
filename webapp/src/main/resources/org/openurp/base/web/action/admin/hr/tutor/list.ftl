[#ftl]
[@b.head/]
[@b.grid items=staffs var="staff"]
  [@b.gridbar]
    function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("code:工号,name:姓名,gender.name:性别,department.name:部门"+
                ",idType.name:证件类型,idNumber:证件号码,birthday:出生日期,nation.name:民族,politicalStatus.name:政治面貌,email:电子邮件"+
                ",mobile:联系手机,staffType.name:教职工类别,title.name:职称,tutorType.name:导师类别,educationDegree.name:最高学历,degreeLevel.name:学位层次,degree.name:最高学位"+
                ",degreeAwardBy:最高学位授予单位,status.name:在职状态,organization:全职工作单位",null,'fileName=导师信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="8%" property="code" title="工号"]${staff.code}[/@]
    [@b.col width="8%" property="name" title="姓名"]
      [@b.a href="!info?id=${staff.id}"]
        <span title="${staff.beginOn!}~${staff.endOn!}">${staff.name}</span>
      [/@]
    [/@]
    [@b.col width="6%" property="gender.name" title="性别"/]
    [@b.col property="department.name" title="院系"]${(staff.department.name)!}[/@]
    [@b.col width="8%" property="staffType.name" title="类别"]${(staff.staffType.name)!}[/@]
    [@b.col width="8%" property="title.name" title="职称"]${(staff.title.name)!}[/@]
    [@b.col property="tutorType.name" width="9%" title="导师类别"/]
    [@b.col width="9%" property="degree.name" title="学位"/]
    [@b.col width="10%" property="beginOn" title="任教起始"]${staff.beginOn?string('yyyy-MM')}~${(staff.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
