[#ftl]
[@b.head/]
[@b.toolbar title="修改假日安排"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if holiday.persisted]!update?id=${holiday.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="holiday.name" label="名称" value="${holiday.name!}" required="true" maxlength="20"/]
    [@b.startend label="生效失效时间" 
      name="holiday.beginOn,holiday.endOn" required="true,true" 
      start=holiday.beginOn end=holiday.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]