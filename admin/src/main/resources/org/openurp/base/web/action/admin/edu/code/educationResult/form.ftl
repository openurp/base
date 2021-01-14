[#ftl]
[@b.head/]
[@b.toolbar title="修改毕结业结论"]bar.addBack();[/@]
[@b.tabs]
  [@b.form  theme="list" action=b.rest.save(educationResult)]
    [@b.textfield name="educationResult.code" label="代码" value="${educationResult.code!}" required="true" maxlength="20"/]
    [@b.textfield name="educationResult.name" label="名称" value="${educationResult.name!}" required="true" maxlength="20"/]
    [@b.textfield name="educationResult.enName" label="英文名" value="${educationResult.enName!}" maxlength="100"/]
    [@b.startend label="有效期"
      name="educationResult.beginOn,educationResult.endOn" required="true,false"
      start=educationResult.beginOn end=educationResult.endOn format="date"/]
    [@b.textfield name="educationResult.remark" label="备注" value="${educationResult.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]