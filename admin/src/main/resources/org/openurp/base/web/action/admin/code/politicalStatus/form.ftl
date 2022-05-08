[#ftl]
[@b.head/]
[@b.toolbar title="新建政治面貌信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(politicalStatus) theme="list"]
    [@b.textfield name="politicalStatus.code" label="代码" value="${politicalStatus.code}" required="true" maxlength="20"/]
    [@b.textfield name="politicalStatus.name" label="名称" value="${politicalStatus.name}" required="true" maxlength="200"/]
    [@b.textfield name="politicalStatus.enName" label="英文名称" value="${politicalStatus.enName!}" maxlength="200"/]
    [@b.startend label="有效期限"
      name="politicalStatus.beginOn,politicalStatus.endOn" required="false,false"
      start=politicalStatus.beginOn end=politicalStatus.endOn format="date"/]
    [@b.textfield name="politicalStatus.remark" label="备注" value="${politicalStatus.remark!}" maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
