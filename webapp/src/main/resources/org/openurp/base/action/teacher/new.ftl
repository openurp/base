[#ftl]
[@b.head/]
[@b.toolbar title="新建教师信息"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]
    [@b.textfield name="teacher.code" label="职工号" value="${teacher.code!}" required="true" maxlength="20"/]
    [@b.textfield name="teacher.name" label="姓名" value="${teacher.name!}" required="true" maxlength="20"/]
    [@b.textfield name="teacher.school" label="毕业学校" value="${teacher.school!}" maxlength="100"/]
    [@b.startend label="生效失效日期" 
      name="teacher.beginOn,teacher.endOn" required="false,false" 
      start=teacher.beginOn end=teacher.endOn format="date"/]
    [@b.textfield name="teacher.remark" label="备注" value="${teacher.remark!}" maxlength="30"/]
    [@b.select name="teacher.person.id" label="人员信息" value="${(teacher.person.id)!}" required="true" 
               style="width:200px;" items=persons option="id,name" empty="..."/]
    [@b.select name="teacher.department.id" label="部门" value="${(teacher.department.id)!}" required="true" 
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="teacher.parttimeDepart.id" label="兼职部门" value="${(teacher.parttimeDepart.id)!}" required="true" 
               style="width:200px;" items=parttimeDeparts option="id,name" empty="..."/]
    [@b.select name="teacher.title.id" label="职称" value="${(teacher.title.id)!}"  
               style="width:200px;" items=titles option="id,name" empty="..."/]
    [@b.select name="teacher.teacherType.id" label="教职工类别" value="${(teacher.teacherType.id)!}"  
               style="width:200px;" items=teacherTypes option="id,name" empty="..."/]
    [@b.select name="teacher.education.id" label="学历" value="${(teacher.education.id)!}" required="true" 
               style="width:200px;" items=educations option="id,name" empty="..."/]
    [@b.select name="teacher.degree.id" label="学位" value="${(teacher.degree.id)!}" required="true" 
               style="width:200px;" items=degrees option="id,name" empty="..."/]
    [@b.select name="teacher.tutorType.id" label="导师类别" value="${(teacher.tutorType.id)!}" 
               style="width:200px;" items=tutorTypes option="id,name" empty="..."/]
    [@b.select name="teacher.unitType.id" label="=聘任单位的类别" value="${(teacher.unitType.id)!}" 
               style="width:200px;" items=unitTypes option="id,name" empty="..."/]
    [@b.select name="teacher.state.id" label="=教师在职状态" value="${(teacher.state.id)!}" 
               style="width:200px;" items=states option="id,name" empty="..."/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]