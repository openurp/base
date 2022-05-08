[#ftl]
[@b.head/]
[@b.toolbar title="修改专业建设过程"]bar.addBack();[/@]
  [@b.form action=b.rest.save(majorDiscipline) theme="list"]
    [@b.select name="majorDiscipline.category.id" label="学科门类" value="${(majorDiscipline.category.id)!}" required="true"
               style="width:200px;" items=categories option="id,name" empty="..."/]
    [@b.textfield name="majorDiscipline.disciplineCode" label="教育部代码" value="${majorDiscipline.disciplineCode!}"  required="true" maxlength="30"/]
    [@b.startend label="有效期"
      name="majorDiscipline.beginOn,majorDiscipline.endOn" required="true,false"
      start=majorDiscipline.beginOn end=majorDiscipline.endOn format="date"/]
    [@b.textfield name="majorDiscipline.remark" label="备注" value="${majorDiscipline.remark!}" maxlength="30"/]
    [@b.formfoot]
      <input type="hidden" name="majorDiscipline.major.id" value="${(majorDiscipline.major.id)!}">
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
