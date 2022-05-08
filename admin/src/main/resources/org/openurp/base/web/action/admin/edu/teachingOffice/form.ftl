[#ftl]
[@b.head/]
[@b.toolbar title="教研室信息"]bar.addBack();[/@]
  [@b.form action=sa theme="list" action=b.rest.save(teachingOffice)]
    [@b.textfield name="teachingOffice.code" label="代码" value="${teachingOffice.code!}" required="true" maxlength="20"/]
    [@b.textfield name="teachingOffice.name" label="名称" value="${teachingOffice.name!}" required="true" maxlength="20"/]
    [@b.select name="teachingOffice.department.id" label="院系" value="${(teachingOffice.department.id)!}" required="true"
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="teachingOffice.director.id" label="负责人" value=teachingOffice.director!
               style="width:300px;" href=urp.api+"/base/users.json?isTeacher=1&q={term}" option="id,name" empty="..."/]
    [@b.select name="member.id" label="成员" multiple="true" values=teachingOffice.members
               style="width:300px;" href=urp.api+"/base/users.json?isTeacher=1&q={term}" option="id,name" empty="..."/]
    [@b.startend label="有效期"
      name="teachingOffice.beginOn,teachingOffice.endOn" required="true,false"
      start=teachingOffice.beginOn end=teachingOffice.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
