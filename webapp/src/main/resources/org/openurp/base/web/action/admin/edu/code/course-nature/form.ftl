[#ftl]
[@b.head/]
[@b.toolbar title="修改课程性质"]bar.addBack();[/@]
  [@b.form action=b.rest.save(code) theme="list"]
    [@b.textfield name="code.code" label="代码" value="${code.code!}" required="true" maxlength="20"/]
    [@b.textfield name="code.name" label="名称" value="${code.name!}" required="true" maxlength="20"/]
    [@b.textfield name="code.enName" label="英文名" value="${code.enName!}" maxlength="100"/]
    [@b.radios label="是否实践课"  name="code.practical" value=code.practical items="1:common.yes,0:common.no"/]
    [@b.startend label="有效期"
      name="code.beginOn,code.endOn" required="true,false"
      start=code.beginOn end=code.endOn format="date"/]
    [@b.textfield name="code.remark" label="备注" value="${code.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]