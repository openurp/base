[#ftl]
[@b.head/]
[@b.toolbar title="新建通用人员信息"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]
    [@b.textfield name="person.code" label="学工号" value="${person.code!}" required="true" maxlength="30"/]
    [@b.textfield name="person.name" label="姓名" value="${person.name!}" required="true" maxlength="80"/]
    [@urp.codeSelect name="person.gender.id" label="性别" value=(person.gender.id)! empty="..."
               style="width:200px;"  code="/base/code/gender"  required="true"/]
    [@urp.codeSelect name="person.idType.id" label="证件类型" value="${(person.idType.id)!}" empty="..."
               style="width:200px;" code="/base/code/id-type" required="true"/]
    [@b.textfield name="person.sid" label="证件号码" value="${person.sid!}" required="true" maxlength="30"/]
    [@b.datepicker name="person.birthday" label="出生日期" value="${person.birthday!}" /]
    [@b.select name="person.department.id" label="所在部门" value="${person.department!}" 
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@urp.codeSelect name="person.category.id" label="人员分类" value="${person.category!}" style="width:200px;" 
        code="/base/code/person-category"/]
    [@b.textfield name="person.mobile" label="电话" value="${person.mobile!}" maxlength="15"/]
    [@b.textfield name="person.email" label="邮箱" value="${person.email!}" required="true" maxlength="80"/]
    [@urp.codeSelect name="person.country.id" label="所在国家地区" value="${(person.country.id)!}" 
               style="width:200px;" code="/base/code/country" empty="..."/]
    [@b.textfield name="person.address" label="地址" value="${person.address!}" maxlength="200"/]
    [@b.textfield name="person.remark" label="说明" value="${person.remark!}" maxlength="190"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]