[#ftl]
[@b.head/]
[@b.toolbar title="修改性别类型"]bar.addBack();[/@]
  [@b.form action=b.rest.save(gender) theme="list"]
    [@b.textfield name="gender.code" label="代码" value="${gender.code!}" required="true" maxlength="20"/]
    [@b.textfield name="gender.name" label="名称" value="${gender.name!}" required="true" maxlength="20"/]
    [@b.textfield name="gender.enName" label="英文名称" value="${gender.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="gender.beginOn,gender.endOn" required="false,false"
      start=gender.beginOn end=gender.endOn format="date"/]
    [@b.textfield name="gender.remark" label="备注" value="${gender.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
