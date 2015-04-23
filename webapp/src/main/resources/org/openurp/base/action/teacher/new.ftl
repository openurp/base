[#ftl]
[@b.head/]
[@b.toolbar title="新建教师信息"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]
    [@b.textfield name="teacher.code" label="职工号" value="${teacher.code!}" required="true" maxlength="20"/]
    [@b.textfield name="teacher.name" label="姓名" value="${teacher.staff.person.name!}" required="true" maxlength="20"/]
    [@b.startend label="生效失效日期" 
      name="teacher.beginOn,teacher.endOn" required="false,false" 
      start=teacher.beginOn end=teacher.endOn format="date"/]
    [@b.textfield name="teacher.remark" label="备注" value="${teacher.remark!}" maxlength="30"/]
    [@b.select name="teacher.staff.id" label="人员信息" value="${(teacher.staff.id)!}" required="true" 
               style="width:200px;" items=persons option="id,name" empty="..."/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]