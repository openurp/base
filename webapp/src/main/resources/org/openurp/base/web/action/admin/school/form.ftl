[#ftl]
[@b.head/]
[@b.toolbar title="修改学校信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(school) theme="list"]
    [@b.textfield name="school.code" label="代码" value=school.code! required="true" maxlength="10"/]
    [@b.textfield name="school.name" label="名称" value=school.name! required="true" maxlength="50"/]
    [@b.textfield name="school.shortName" label="简称" value=school.shortName! required="false" maxlength="50"/]

    [@b.select name="school.category.id" label="办学类型" value=school.category!
               required="true" items=categories empty="..."/]
    [@b.select name="school.institution.id" label="科研机构" value=school.institution
               required="true" items=institutions option="id,name" empty="..."/]
    [@b.select name="school.division.id" label="所在地区" value=school.division!
               required="true" items=divisions empty="..."/]

    [@b.textfield name="school.identifier" label="标识码" value=school.identifier! required="false" maxlength="10" comment="10位"/]
    [@b.textfield name="school.superiorOrg" label="主管部门" value=school.superiorOrg! required="false"/]
    [@b.textfield name="school.uscc" label="统一信用代码" value=school.uscc! required="false"/]
    [@b.textfield name="school.logoUrl" label="logo网址" value=school.logoUrl! required="true" style="width:400px"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[#list 1..10 as i]<br>[/#list]
[@b.foot/]
