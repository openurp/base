[#ftl]
[@b.head/]
[@b.grid items=majorJournals var="majorJournal"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="level.name" title="培养层次"]${(majorJournal.level.name)!}[/@]
    [@b.col width="45%" property="depart" title="院系"]${(majorJournal.depart.name)!}[/@]
    [@b.col width="10%" property="beginOn" title="生效日期"]${majorJournal.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效日期"]${majorJournal.endOn!}[/@]
    [@b.col width="10%" property="remark" title="备注"]${majorJournal.remark!}[/@]
  [/@]
[/@]
[@b.foot/]
