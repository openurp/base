[#ftl]
[@b.head/]
[@b.toolbar title="修改学生分类标签"]bar.addBack();[/@]
  [@b.form action=b.rest.save(stdLabel) theme="list"]
    [@b.textfield name="stdLabel.code" label="代码" value="${stdLabel.code!}" required="true" maxlength="20"/]
    [@b.textfield name="stdLabel.name" label="名称" value="${stdLabel.name!}" required="true" maxlength="20"/]
    [@b.select name="stdLabel.labelType.id" label="标签类型" value="${(stdLabel.labelType.id)!}" required="true"
               style="width:200px;" items=labelTypes option="id,name" empty="..."/]
    [@b.textfield name="stdLabel.enName" label="英文名" value="${stdLabel.enName!}" maxlength="100"/]
    [@b.startend label="有效期"
      name="stdLabel.beginOn,stdLabel.endOn" required="true,false"
      start=stdLabel.beginOn end=stdLabel.endOn format="date"/]
    [@b.textfield name="stdLabel.remark" label="备注" value="${stdLabel.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
