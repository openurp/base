[#ftl]
[@b.head/]
[@b.toolbar title="修改人员分类"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if userCategory.persisted]!update?id=${userCategory.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="userCategory.code" label="代码" value="${userCategory.code!}" required="true" maxlength="20"/]
    [@b.textfield name="userCategory.name" label="名称" value="${userCategory.name!}" required="true" maxlength="20"/]
    [@b.textfield name="userCategory.enName" label="英文名称" value="${userCategory.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="userCategory.beginOn,userCategory.endOn" required="false,false"
      start=userCategory.beginOn end=userCategory.endOn format="date"/]
    [@b.textfield name="userCategory.remark" label="备注" value="${userCategory.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
