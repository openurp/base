[#ftl]
[@b.head/]
[@b.toolbar title="修改人员分类"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action=b.rest.save(eduCategory) theme="list"]
    [@b.textfield name="eduCategory.code" label="代码" value="${eduCategory.code!}" required="true" maxlength="20"/]
    [@b.textfield name="eduCategory.name" label="名称" value="${eduCategory.name!}" required="true" maxlength="20"/]
    [@b.textfield name="eduCategory.enName" label="英文名称" value="${eduCategory.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="eduCategory.beginOn,eduCategory.endOn" required="false,false"
      start=eduCategory.beginOn end=eduCategory.endOn format="date"/]
    [@b.textfield name="eduCategory.remark" label="备注" value="${eduCategory.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
