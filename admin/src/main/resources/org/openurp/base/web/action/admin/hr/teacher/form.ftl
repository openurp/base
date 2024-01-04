[#ftl]
[@b.head/]
<style>
 .title{
   max-width:100px;
 }
</style>
[@b.toolbar title="修改教师信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(teacher) theme="list"]
    [@b.field label="工号姓名"]
      ${teacher.staff.code} ${teacher.staff.name}
    [/@]
    [@b.select name="teacher.department.id" label="院系" value=teacher.department! required="true" style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="campus.id" label="任教校区" values=teacher.campuses items=campuses required="false" chosenMin="1" multiple="true" style="width:300px;"/]
    [#if teachingOffices?size>0]
    [@b.select name="teacher.office.id" label="教研室" items=teachingOffices value=teacher.office! empty="..." /]
    [/#if]
    [#if tutorTypes?size>0]
    [@b.select name="teacher.tutorType.id" label="导师类别" items=tutorTypes value=teacher.tutorType! empty="..." required="false"/]
    [/#if]
    [@b.textfield name="teacher.tqcNumber" label="教师资格证号码" value=teacher.tqcNumber! maxlength="30"/]
    [@b.textarea name="teacher.oqc" label="其他职业资格证书和等级说明" value=teacher.oqc! maxlength="400" rows="5" cols="80" placeholder="证书 等级 每个一行"/]
    [@b.startend label="任教时间" name="teacher.beginOn,teacher.endOn" required="true,false" start=teacher.beginOn end=teacher.endOn format="date"/]
    [@b.textfield name="teacher.remark" label="备注" value=teacher.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
