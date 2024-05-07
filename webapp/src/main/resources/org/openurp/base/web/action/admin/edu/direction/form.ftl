[#ftl]
[@b.head/]
[@b.toolbar title="修改专业方向"]bar.addBack();[/@]
[@b.tabs]
  [@b.tab label="基本信息"]
   [@b.form action=b.rest.save(direction) theme="list"]
    [@b.textfield name="direction.code" label="代码" value="${direction.code!}" required="true" maxlength="20"/]
    [@b.textfield name="direction.name" label="名称" value="${direction.name!}" required="true" maxlength="30"/]
    [@b.textfield name="direction.enName" label="英文名" value="${direction.engName!}" maxlength="100"/]
    [@b.select name="direction.major.id" label="专业" value=direction.major! option=r"${item.code} ${item.name}" required="true" style="width:300px;" items=majors empty="..."/]
    [@b.startend label="有效期"
      name="direction.beginOn,direction.endOn" required="true,false"
      start=direction.beginOn end=direction.endOn format="date"/]
    [@b.textfield name="direction.remark" label="备注" value="${direction.remark!}" maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
   [/@]
  [/@]
  [#if direction.persisted]
  [@b.tab label="建设过程"]
    [@b.div href="direction-journal!search?directionJournal.direction.id=${direction.id}"/]
  [/@]
  [/#if]
[/@]
[@b.foot/]