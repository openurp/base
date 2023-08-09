[#ftl]
[@b.head/]
<style>
 .title{
   max-width:100px;
 }
</style>
[@b.toolbar title="新增辅导员信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(mentor) theme="list"]
    [@b.select name="mentor.staff.id" label="职工号" required="true" style="width:400px;" items=staffs option=r"${item.code} ${item.name} ${item.department.name}"chosenMin="1"/]
    [@b.startend label="任教时间" name="mentor.beginOn,mentor.endOn" required="true,false" start=mentor.beginOn end=mentor.endOn format="date"/]
    [@b.textfield name="mentor.remark" label="备注" value=mentor.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
