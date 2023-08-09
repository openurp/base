[#ftl]
[@b.head/]
<style>
 .title{
   max-width:100px;
 }
</style>
[@b.toolbar title="修改辅导员信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(mentor) theme="list"]
    [@b.field label="工号姓名"]
      ${mentor.staff.code} ${mentor.staff.name}
    [/@]
    [@b.startend label="任教时间" name="mentor.beginOn,mentor.endOn" required="true,false" start=mentor.beginOn end=mentor.endOn format="date"/]
    [@b.textfield name="mentor.remark" label="备注" value=mentor.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
