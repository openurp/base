[#ftl]
[@b.head/]
[@b.toolbar title="修改学位层次"]bar.addBack();[/@]
[@b.tabs]
  [@b.form  theme="list" action=b.rest.save(degreeLevel)]
    [@b.textfield name="degreeLevel.code" label="代码" value="${degreeLevel.code!}" required="true" maxlength="20"/]
    [@b.textfield name="degreeLevel.name" label="名称" value="${degreeLevel.name!}" required="true" maxlength="20"/]
    [@b.textfield name="degreeLevel.enName" label="英文名" value="${degreeLevel.enName!}" maxlength="100"/]
    [@b.startend label="有效期"
      name="degreeLevel.beginOn,degreeLevel.endOn" required="true,false"
      start=degreeLevel.beginOn end=degreeLevel.endOn format="date"/]
    [@b.textfield name="degreeLevel.remark" label="备注" value="${degreeLevel.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
