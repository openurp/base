[#ftl]
[@b.head/]
[@b.toolbar title="修改出版社"]bar.addBack();[/@]
  [@b.form action=b.rest.save(code) theme="list"]
    [@b.textfield name="code.code" label="代码" value="${code.code!}" required="true" maxlength="20"/]
    [@b.textfield name="code.name" label="名称" value="${code.name!}" required="true" maxlength="20"/]
    [@b.textfield name="code.enName" label="英文名" value="${code.enName!}" maxlength="100"/]
    [@b.select name="code.grade.id" label="级别" value=(code.grade.id)!
               style="width:200px;" items=grades empty="..." required="true"/]
    [@b.startend label="有效期"
      name="code.beginOn,code.endOn" required="true,false"
      start=code.beginOn end=code.endOn format="date"/]
    [@b.textfield name="code.remark" label="备注" value="${code.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
