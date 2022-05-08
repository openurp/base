[#ftl]
[@b.head/]
[@b.toolbar title="修改专业建设过程"]bar.addBack();[/@]
  [@b.form action=b.rest.save(majorJournal) theme="list"]
    [@b.select name="majorJournal.level.id" label="培养层次" value="${(majorJournal.level.id)!}" required="true"
               style="width:200px;" items=levels option="id,name" empty="..."/]
    [@b.select name="majorJournal.depart.id" label="院系" value="${(majorJournal.depart.id)!}" required="true"
               style="width:200px;" items=departs option="id,name" empty="..."/]
    [@b.startend label="有效期"
      name="majorJournal.beginOn,majorJournal.endOn" required="true,false"
      start=majorJournal.beginOn end=majorJournal.endOn format="date"/]
    [@b.textfield name="majorJournal.remark" label="备注" value="${majorJournal.remark!}" maxlength="30"/]
    [@b.formfoot]
      <input type="hidden" name="majorJournal.major.id" value="${majorJournal.major.id}"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
