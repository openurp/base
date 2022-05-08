[#ftl]
[@b.head/]
[@b.toolbar title="修改学科门类"]bar.addBack();[/@]
  [@b.form  theme="list" action=b.rest.save(disciplineCategory)]
    [@b.textfield name="disciplineCategory.code" label="代码" value="${disciplineCategory.code!}" required="true" maxlength="20"/]
    [@b.textfield name="disciplineCategory.name" label="名称" value="${disciplineCategory.name!}" required="true" maxlength="20"/]
    [@b.textfield name="disciplineCategory.enName" label="英文名" value="${disciplineCategory.enName!}" maxlength="100"/]
    [@b.startend label="有效期"
      name="disciplineCategory.beginOn,disciplineCategory.endOn" required="true,false"
      start=disciplineCategory.beginOn end=disciplineCategory.endOn format="date"/]
    [@b.textfield name="disciplineCategory.remark" label="备注" value="${disciplineCategory.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
