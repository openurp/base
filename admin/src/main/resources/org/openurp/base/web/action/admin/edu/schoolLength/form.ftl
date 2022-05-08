[#ftl]
[@b.head/]
[@b.toolbar title="学习年限"]bar.addBack();[/@]
  [#assign sa][#if schoolLength.persisted]!update?id=${schoolLength.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list" action=b.rest.save(schoolLength)]
    [@b.select name="schoolLength.level.id" label="培养层次" value="${(schoolLength.level.id)!}" required="true"
               style="width:200px;" items=levels option="id,name" empty="..."/]
    [@b.textfield name="schoolLength.fromGrade" label="起始年级" value=schoolLength.fromGrade! required="true"/]
    [@b.textfield name="schoolLength.toGrade" label="截止年级" value=schoolLength.toGrade! required="false"/]

    [@b.textfield name="schoolLength.normal" label="学制" value=schoolLength.normal! required="true" comment="年"/]
    [@b.textfield name="schoolLength.minimum" label="最低学习年限" value=schoolLength.minimum! required="true"/]
    [@b.textfield name="schoolLength.maximum" label="最长学习年限" value=schoolLength.maximum! required="true"/]
    [@b.formfoot]
      <input type="hidden" name="schoolLength.major.id" value="${schoolLength.major.id}"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
