[#ftl]
[@b.head/]
<style>
form.listform label.title{width:120px}
</style>
[@b.toolbar title="修改教学日历方案类型"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action=b.rest.save(calendar) theme="list"]
    [@b.textfield name="calendar.code" label="代码" value="${calendar.code!}" required="true" maxlength="10"/]
    [@b.textfield name="calendar.name" label="名称" value="${calendar.name!}" required="true" maxlength="80"/]
    [@b.select name="calendar.school.id" label="学校" value=calendar.school!  style="width:200px;" items=schools required="true"/]
    [@b.radios name="calendar.firstWeekday" label="每周开始时间" value=((calendar.firstWeekday.id)?string)!"1"
        required="true" items="7:周日,1:周一"/]
    [@b.startend label="有效期"
      name="calendar.beginOn,calendar.endOn" required="true,false"
      start=calendar.beginOn end=calendar.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
