[#ftl]
[@b.head/]
[@b.toolbar title="修改语种类型"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if language.persisted]!update?id=${language.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="language.code" label="代码" value="${language.code!}" required="true" maxlength="20"/]
    [@b.textfield name="language.name" label="名称" value="${language.name!}" required="true" maxlength="20"/]
    [@b.textfield name="language.enName" label="英文名称" value="${language.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="language.beginOn,language.endOn" required="false,false"
      start=language.beginOn end=language.endOn format="date"/]
    [@b.textfield name="language.remark" label="备注" value="${language.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
