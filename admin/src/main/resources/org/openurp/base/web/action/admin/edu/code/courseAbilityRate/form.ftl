[#ftl]
[@b.head/]
[@b.toolbar title="修改课程能力等级"]bar.addBack();[/@]
  [@b.form action=b.rest.save(courseAbilityRate) theme="list"]
    [@b.textfield name="courseAbilityRate.code" label="代码" value="${courseAbilityRate.code!}" required="true" maxlength="20"/]
    [@b.textfield name="courseAbilityRate.name" label="名称" value="${courseAbilityRate.name!}" required="true" maxlength="20"/]
    [@b.textfield name="courseAbilityRate.enName" label="英文名" value="${courseAbilityRate.enName!}" maxlength="100"/]
    [@b.textfield name="courseAbilityRate.rate" label="等级值" value="${courseAbilityRate.rate!}" required="true" maxlength="3"/]
    [@b.startend label="有效期"
      name="courseAbilityRate.beginOn,courseAbilityRate.endOn" required="true,false"
      start=courseAbilityRate.beginOn end=courseAbilityRate.endOn format="date"/]
    [@b.textfield name="courseAbilityRate.remark" label="备注" value="${courseAbilityRate.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
