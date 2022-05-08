[#ftl]
[@b.head/]
[@b.toolbar title="修改学位"]bar.addBack();[/@]
  [@b.form action=b.rest.save(degree) theme="list"]
    [@b.textfield name="degree.code" label="代码" value="${degree.code!}" required="true" maxlength="20"/]
    [@b.textfield name="degree.name" label="名称" value="${degree.name!}" required="true" maxlength="20"/]
    [@b.textfield name="degree.enName" label="英文名" value="${degree.enName!}" maxlength="100"/]
    [@b.select name="degree.level.id" label="级别" value=(course.grade.id)!
               style="width:200px;" items=levels empty="..." required="true"/]
    [@b.startend label="有效期"
      name="degree.beginOn,degree.endOn" required="true,false"
      start=degree.beginOn end=degree.endOn format="date"/]
    [@b.textfield name="degree.remark" label="备注" value="${degree.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
