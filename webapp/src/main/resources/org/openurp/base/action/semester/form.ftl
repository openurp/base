[#ftl]
[@b.head/]
<style>
form.listform label.title{width:120px}
</style>
[@b.toolbar title="修改学年学期类型"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!update?id=${semester.id}" theme="list"]
    [@b.textfield name="semester.code" label="代码" value="${semester.code!}" required="true" maxlength="15"/]
    [@b.textfield name="semester.name" label="学年学期名称" value="${semester.name!}" required="true" maxlength="10"/]
    [@b.textfield name="semester.schoolYear" label="学年度" value="${semester.schoolYear!}"  required="true" maxlength="10"/]
    [@b.select name="semester.calendar.id" label="日历方案" value="${semester.calendar!}" 
               style="width:200px;" items=calendars option="id,name" empty="..." required="true"/]
    [@b.radios name="firstDay" label="每周开始时间" value="1"
        required="true" items="7:周日,1:周一"/]
    [@b.startend label="生效失效时间" 
      name="semester.beginOn,semester.endOn" required="true,true" 
      start=semester.beginOn end=semester.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]