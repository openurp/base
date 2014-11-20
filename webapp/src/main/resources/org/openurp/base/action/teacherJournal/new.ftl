[#ftl]
[@b.head/]
[@b.toolbar title="新建教师日志信息"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!save" theme="list"]
    [@b.startend label="生效失效日期" 
      name="teacherJournal.beginOn,teacherJournal.endOn" required="false,false" 
      start=teacherJournal.beginOn end=teacherJournal.endOn format="date"/]
    [@b.textfield name="teacherJournal.remark" label="备注" value="${teacherJournal.remark!}" maxlength="30"/]
    [@b.select name="teacherJournal.teacher.id" label="教师" value="${(teacherJournal.teacher.id)!}" required="true" 
               style="width:200px;" items=teachers option="id,name" empty="..."/]
    [@b.select name="teacherJournal.department.id" label="部门" value="${(teacherJournal.department.id)!}" required="true" 
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="teacherJournal.title.id" label="职称" value="${(teacherJournal.title.id)!}" required="true" 
               style="width:200px;" items=titles option="id,name" empty="..."/]
    [@b.select name="teacherJournal.education.id" label="学历" value="${(teacherJournal.education.id)!}" required="true" 
               style="width:200px;" items=educations option="id,name" empty="..."/]
    [@b.select name="teacherJournal.degree.id" label="学位" value="${(teacherJournal.degree.id)!}" required="true" 
               style="width:200px;" items=degrees option="id,name" empty="..."/]
    [@b.select name="teacherJournal.tutorType.id" label="导师类别" value="${(teacherJournal.tutorType.id)!}" 
               style="width:200px;" items=tutorTypes option="id,name" empty="..."/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]