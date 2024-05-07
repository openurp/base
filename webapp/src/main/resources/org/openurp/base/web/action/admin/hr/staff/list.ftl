[#ftl]
[@b.head/]
[@b.grid items=staffs var="staff"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("${b.text("action.export")}",action.exportData("code:工号,name:姓名,gender.name:性别,department.name:部门"+
                ",idType.name:证件类型,idNumber:证件号码,birthday:出生日期,nation.name:民族,politicalStatus.name:政治面貌,email:电子邮件"+
                ",mobile:联系手机,staffType.name:类别,title.name:职称,educationDegree.name:最高学历,degreeLevel.name:学位层次,degree.name:最高学位"+
                ",degreeAwardBy:最高学位授予单位,status.name:在职状态,organization:全职工作单位,formalHr:是否在编,external:是否外聘"+
                ",parttime:是否兼职,beginOn:生效日期",null,'fileName=教职工信息'));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="工号"]${staff.code}[/@]
    [@b.col width="10%" property="name" title="姓名"]
      [@b.a href="!info?id=${staff.id}"]
        <span title="${staff.beginOn!}~${staff.endOn!}">${staff.name}[#if staff.external]<sup>外聘</sup>[/#if][#if staff.parttime]<sup>兼职</sup>[/#if]</span>
      [/@]
    [/@]
    [@b.col width="6%" property="gender.name" title="性别"/]
    [@b.col property="department.name" title="部门"]${(staff.department.name)!}[/@]
    [@b.col width="10%" property="staffType.name" title="类别"]${(staff.staffType.name)!}[/@]
    [@b.col width="10%" property="title.name" title="职称"]${(staff.title.name)!}[/@]
    [@b.col width="12%" property="degree.name" title="学位"/]
    [@b.col width="10%" property="status.name" title="在职状态"/]
    [@b.col width="10%" property="beginOn" title="在校时间"]${staff.beginOn?string('yyyy-MM')}~${(staff.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
