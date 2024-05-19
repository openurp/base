[#ftl]
[@b.head/]
[@b.toolbar title="修改考试情况"]bar.addBack();[/@]
  [@b.form action=b.rest.save(code) theme="list"]
    [@b.textfield name="code.code" label="代码" value="${code.code!}" required="true" maxlength="20"/]
    [@b.textfield name="code.name" label="名称" value="${code.name!}" required="true" maxlength="20"/]
    [@b.textfield name="code.enName" label="英文名" value="${code.enName!}" maxlength="100"/]

    [@b.radios label="是否参加考试"  name="code.attended" value=code.attended items="1:common.yes,0:common.no"/]
    [@b.radios label="是否需要参加下一次缓考"  name="code.hasDeferred" value=code.hasDeferred items="1:common.yes,0:common.no"/]
    [@b.radios label="是否有作弊行为"  name="code.cheating" value=code.cheating items="1:common.yes,0:common.no"/]

    [@b.startend label="有效期"
      name="code.beginOn,code.endOn" required="true,false"
      start=code.beginOn end=code.endOn format="date"/]
    [@b.textfield name="code.remark" label="备注" value="${code.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
