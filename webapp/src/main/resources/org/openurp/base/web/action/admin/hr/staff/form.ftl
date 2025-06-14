[#ftl]
[@b.head/]
[@b.toolbar title="修改教职工信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(staff) theme="list"]
    [@b.textfield name="staff.code" label="职工号" value=staff.code! required="true" style="width:100px;" maxlength="20"/]
    [@b.textfield name="staff.name" label="姓名" id="name" value=staff.name! required="true" style="width:100px;" maxlength="20"/]
    [@b.textfield label="姓名拼音" id="phoneticName" name="staff.enName" value=(staff.enName)! maxlength="100" style="width: 120px"]
      <a href="javascript:void(0)" style="margin-left: 10px;" onclick="return auto_pinyin()">获取拼音</a>
    [/@]

    [@b.select name="staff.gender.id" label="性别" value=staff.gender! required="true"  items=genders empty="..."/]
    [@b.select name="staff.idType.id" label="证件类型" value=staff.idType! required="true"  items=idTypes  empty="..."/]
    [@b.textfield name="staff.idNumber" label="证件号码" value=staff.idNumber! required=extraRequired?seq_contains("idNumber")?c maxlength="20"/]
    [@b.date name="staff.birthday" label="出生日期" value=staff.birthday! required="false"/]
    [@b.select name="staff.nation.id" label="民族" value=staff.nation! required="false"  items=nations  empty="..."/]
    [@b.select name="staff.politicalStatus.id" label="政治面貌" value=staff.politicalStatus! required="false"  items=politicalStatuses empty="..."/]

    [@b.select name="staff.staffType.id" label="教职工类别" value=staff.staffType! required="true"  items=staffTypes/]
    [@b.select name="staff.department.id" label="所在部门" value=staff.department! required="true" style="width:200px;" items=departments empty="..."/]
    [@b.select name="staff.title.id" label="职称" value=staff.title! required=extraRequired?seq_contains("title")?c items=titles/]
    [@b.select name="staff.educationDegree.id" label="最高学历" value=staff.educationDegree! required=extraRequired?seq_contains("educationDegree")?c items=educationDegrees/]
    [@b.select name="staff.degreeLevel.id" label="学位水平" value=staff.degreeLevel! required=extraRequired?seq_contains("degreeLevel")?c items=degreeLevels/]
    [@b.select name="staff.degree.id" label="最高学位" value=staff.degree! required="false" items=degrees/]
    [@b.textfield name="staff.degreeAwardBy" label="学位授予单位" value=staff.degreeAwardBy! required="false" maxlength="100" style="width:300px"/]
    [#if tutorTypes?size>0]
    [@b.select name="staff.tutorType.id" label="导师类型" value=staff.tutorType! required="false" items=tutorTypes/]
    [/#if]

    [@b.radios label="是否在编"  name="staff.formalHr" value=staff.formalHr items="1:common.yes,0:common.no" required="true"/]
    [@b.radios label="是否外聘"  name="staff.external" value=staff.external items="1:common.yes,0:common.no" required="true"/]
    [@b.radios label="是否兼职"  name="staff.parttime" value=staff.parttime items="1:common.yes,0:common.no" required="true"/]
    [@b.textfield label="全职工作单位"  name="staff.organization" value=staff.organization! required="false" /]
    [@b.email label="电子邮件"  name="staff.email" value=staff.email! required="false" /]
    [@b.textfield label="移动电话"  name="staff.mobile" value=staff.mobile! required=extraRequired?seq_contains("mobile")?c /]
    [@b.textfield label="个人主页"  name="staff.homepage" value=staff.homepage! required="false" maxlength="150" style="width:400px"/]
    [@b.startend label="在校时间" name="staff.beginOn,staff.endOn" required="true,false" start=staff.beginOn end=staff.endOn format="date"/]
    [@b.select name="staff.status.id" label="在职状态" value=staff.status! required="true"  items=statuses/]
    [@b.textfield name="staff.remark" label="备注" value=staff.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
<script>
  function auto_pinyin(){
    var name = document.getElementById('name').value;
    if(name) {
      name = encodeURIComponent(name);
      $.get("${api}/tools/sns/person/pinyin/"+name+".json",function(data,status){
          jQuery('#phoneticName').val(data);
      });
    }
    return false;
  }
</script>
[@b.foot/]
