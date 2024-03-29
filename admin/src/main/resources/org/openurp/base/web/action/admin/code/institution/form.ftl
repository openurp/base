[#ftl]
[@b.head/]
[@b.toolbar title="修改科研机构类型"]bar.addBack();[/@]
  [@b.form action=b.rest.save(institution) theme="list"]
    [@b.textfield name="institution.code" label="代码" value="${institution.code!}" required="true" maxlength="20"/]
    [@b.textfield name="institution.name" label="名称" value="${institution.name!}" required="true" maxlength="20"/]
    [@b.textfield name="institution.enName" label="英文名称" value="${institution.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="institution.beginOn,institution.endOn" required="true,false"
      start=institution.beginOn end=institution.endOn format="date"/]
    [@b.textfield name="institution.remark" label="备注" value="${institution.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
