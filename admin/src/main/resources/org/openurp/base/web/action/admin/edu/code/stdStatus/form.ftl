[#ftl]
[@b.head/]
[@b.toolbar title="修改学生学籍状态"]bar.addBack();[/@]
  [#assign sa][#if stdStatus.persisted]!update?id=${stdStatus.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="stdStatus.code" label="代码" value="${stdStatus.code!}" required="true" maxlength="20"/]
    [@b.textfield name="stdStatus.name" label="名称" value="${stdStatus.name!}" required="true" maxlength="20"/]
    [@b.textfield name="stdStatus.enName" label="英文名" value="${stdStatus.enName!}" maxlength="100"/]
    [@b.startend label="有效期"
      name="stdStatus.beginOn,stdStatus.endOn" required="true,false"
      start=stdStatus.beginOn end=stdStatus.endOn format="date"/]
    [@b.textfield name="stdStatus.remark" label="备注" value="${stdStatus.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
