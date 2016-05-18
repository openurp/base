[#ftl]
[@b.head/]
<style>
form.listform label.title{width:120px}
</style>
[@b.toolbar title="修改学年学期类型"]bar.addBack();[/@]
  [#assign sa][#if semester.persisted]!update?id=${semester.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="semester.code" label="代码" value="${semester.code!}" required="true" maxlength="15"/]
    [@b.textfield name="semester.name" label="名称" value="${semester.name!}" required="true" maxlength="10"/]
    [@b.textfield name="semester.schoolYear" label="学年度" value="${semester.schoolYear!}"  required="true" maxlength="10"/]
    [@b.select name="semester.calendar.id" label="日历方案" value="${(semester.calendar.id)!}" 
               style="width:200px;" href=urp.service("/base/calendars") empty="..." required="true"/]
    [@b.startend label="生效失效时间" 
      name="semester.beginOn,semester.endOn" required="true,true" 
      start=semester.beginOn end=semester.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]