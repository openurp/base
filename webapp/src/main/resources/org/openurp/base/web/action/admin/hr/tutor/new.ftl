[#ftl]
[@b.head/]
<style>
 .title{
   max-width:100px;
 }
</style>
[@b.toolbar title="新增导师信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(tutor) theme="list"]
    [@b.select name="tutor.staff.id" label="职工号" required="true" style="width:400px;" items=staffs option=r"${item.code} ${item.name} ${item.department.name}"chosenMin="1"/]
    [@b.select name="tutor.tutorType.id" label="导师类别" items=tutorTypes value=tutor.tutorType! empty="..." required="true"/]
    [@b.startend label="有效期" name="tutor.beginOn,tutor.endOn" required="true,false" start=tutor.beginOn end=tutor.endOn format="date"/]
    [@b.textfield name="tutor.remark" label="备注" value=tutor.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
