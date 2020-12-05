[#ftl]
[@b.head/]
[@b.toolbar title="教研室信息"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action=sa theme="list" action=b.rest.save(teachingGroup)]
    [@b.textfield name="teachingGroup.code" label="代码" value="${teachingGroup.code!}" required="true" maxlength="20"/]
    [@b.textfield name="teachingGroup.name" label="名称" value="${teachingGroup.name!}" required="true" maxlength="20"/]
    [@b.select name="teachingGroup.department.id" label="院系" value="${(teachingGroup.department.id)!}" required="true"
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="teachingGroup.director.id" label="负责人" value=teachingGroup.director!
               style="width:300px;" href=urp.api+"/edu/base/"+project.id+"/users.json?isTeacher=1&q={term}" option="id,name" empty="..."/]
    [@b.select name="member.id" label="成员" multiple="true" values=teachingGroup.members
               style="width:300px;" href=urp.api+"/edu/base/"+project.id+"/users.json?isTeacher=1&q={term}" option="id,name" empty="..."/]
    [@b.startend label="有效期"
      name="teachingGroup.beginOn,teachingGroup.endOn" required="true,false"
      start=teachingGroup.beginOn end=teachingGroup.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
