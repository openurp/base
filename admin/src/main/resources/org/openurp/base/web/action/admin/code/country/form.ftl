[#ftl]
[@b.head/]
[@b.toolbar title="新建国家信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(country) theme="list"]
    [@b.textfield name="country.code" label="代码" value=country.code! required="true" maxlength="20"/]
    [@b.textfield name="country.name" label="名称" value=country.name! required="true" maxlength="200"/]
    [@b.textfield name="country.enName" label="英文名称" value=country.enName! maxlength="200"/]
    [@b.textfield name="country.alpha2Code" label="2位字母代码" value=country.alpha2Code! required="true" maxlength="2"/]
    [@b.textfield name="country.alpha3Code" label="3位字母代码" value=country.alpha3Code! required="true" maxlength="3"/]
    [@b.startend label="有效期限"
      name="country.beginOn,country.endOn" required="true,false"
      start=country.beginOn end=country.endOn format="date"/]
    [@b.textfield name="country.remark" label="备注" value="${country.remark!}" maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
