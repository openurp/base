[#ftl]
[@b.head/]
[@b.toolbar title="修改出版社"]bar.addBack();[/@]
  [@b.form action=b.rest.save(press) theme="list"]
    [@b.textfield name="press.code" label="代码" value="${press.code!}" required="true" maxlength="20"/]
    [@b.textfield name="press.name" label="名称" value="${press.name!}" required="true" maxlength="20"/]
    [@b.textfield name="press.enName" label="英文名" value="${press.enName!}" maxlength="100"/]
    [@b.select name="press.grade.id" label="级别" value=(press.grade.id)!
               style="width:200px;" items=grades empty="..." required="true"/]
    [@b.startend label="有效期"
      name="press.beginOn,press.endOn" required="true,false"
      start=press.beginOn end=press.endOn format="date"/]
    [@b.textfield name="press.remark" label="备注" value="${press.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
