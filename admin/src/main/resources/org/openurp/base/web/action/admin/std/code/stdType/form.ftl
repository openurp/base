[#ftl]
[@b.head/]
[@b.toolbar title="修改学生类别"]bar.addBack();[/@]
  [#assign sa][#if stdType.persisted]!update?id=${stdType.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="stdType.code" label="代码" value="${stdType.code!}" required="true" maxlength="20"/]
    [@b.textfield name="stdType.name" label="名称" value="${stdType.name!}" required="true" maxlength="20"/]
    [@b.textfield name="stdType.enName" label="英文名" value="${stdType.enName!}" maxlength="100"/]
    [@b.startend label="有效期"
      name="stdType.beginOn,stdType.endOn" required="true,false"
      start=stdType.beginOn end=stdType.endOn format="date"/]
    [@b.textfield name="stdType.remark" label="备注" value="${stdType.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
