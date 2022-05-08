[#ftl]
[@b.head/]
[@b.toolbar title="修改考试情况"]bar.addBack();[/@]
  [@b.form action=b.rest.save(examStatus) theme="list"]
    [@b.textfield name="examStatus.code" label="代码" value="${examStatus.code!}" required="true" maxlength="20"/]
    [@b.textfield name="examStatus.name" label="名称" value="${examStatus.name!}" required="true" maxlength="20"/]
    [@b.textfield name="examStatus.enName" label="英文名" value="${examStatus.enName!}" maxlength="100"/]

    [@b.radios label="是否参加考试"  name="examStatus.attended" value=examStatus.attended items="1:common.yes,0:common.no"/]
    [@b.radios label="是否需要参加下一次缓考"  name="examStatus.hasDeferred" value=examStatus.hasDeferred items="1:common.yes,0:common.no"/]
    [@b.radios label="是否有作弊行为"  name="examStatus.cheating" value=examStatus.cheating items="1:common.yes,0:common.no"/]

    [@b.startend label="有效期"
      name="examStatus.beginOn,examStatus.endOn" required="true,false"
      start=examStatus.beginOn end=examStatus.endOn format="date"/]
    [@b.textfield name="examStatus.remark" label="备注" value="${examStatus.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
