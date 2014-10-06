[#ftl]
[@b.head/]
[@b.toolbar title="新建通用人员信息"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]
    [@b.textfield name="personBean.code" label="学工号" value="${personBean.code!}" required="true" maxlength="30"/]
    [@b.textfield name="personBean.name" label="姓名" value="${personBean.name!}" required="true" maxlength="80"/]
    [@b.select name="personBean.gender.id" label="性别" value="${personBean.gender!}" 
               style="width:200px;" items=genders option="id,name" empty="..."/]
    [@b.select name="personBean.idType.id" label="证件类型" value="${personBean.idType!}" 
               style="width:200px;" items=idTypes option="id,name" empty="..." required="true"/]
    [@b.textfield name="personBean.sid" label="证件号码" value="${personBean.sid!}" required="true" maxlength="30"/]
    [@b.datepicker name="personBean.birthday" label="出生日期" value="${personBean.birthday!}" /]
    [#--
    --]
    [@b.select name="personBean.department.id" label="所在部门" value="${personBean.department!}" 
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="personBean.category.id" label="人员分类" value="${personBean.category!}" 
               style="width:200px;" items=categorys option="id,name" empty="..."/]
    [@b.textfield name="personBean.mobile" label="电话" value="${personBean.mobile!}" maxlength="15"/]
    [@b.textfield name="personBean.email" label="邮箱" value="${personBean.email!}" required="true" maxlength="80"/]
    [@b.select name="personBean.country.id" label="所在国家地区" value="${personBean.country!}" 
               style="width:200px;" items=countries option="id,name" empty="..."/]
    [@b.textfield name="personBean.address" label="地址" value="${personBean.address!}" maxlength="200"/]
    [@b.textfield name="personBean.remark" label="说明" value="${personBean.remark!}" maxlength="190"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]