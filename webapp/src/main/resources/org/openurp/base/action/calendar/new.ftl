[#ftl]
[@b.head/]
<style>
form.listform label.title{width:120px}
</style>
[@b.toolbar title="新建教学日历方案类型"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list" ]
    [@b.textfield name="calendar.code" label="代码" value="${calendar.code!}" required="true" maxlength="10"/]
    [@b.textfield name="calendar.name" label="名称" value="${calendar.name!}" required="true" maxlength="80" /]
    [@b.radios name="firstDay" label="每周开始时间" value="1"
        required="true" items="7:周日,1:周一"/]
    [@b.startend label="生效失效时间" 
      name="calendar.beginOn,calendar.endOn" required="false,false" 
      start=calendar.beginOn end=calendar.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]