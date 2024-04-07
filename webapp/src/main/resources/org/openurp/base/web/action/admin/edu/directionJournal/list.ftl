[#ftl]
[@b.head/]
[@b.grid items=directionJournals var="directionJournal"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="20%" property="level" title="培养层次"]${(directionJournal.level.name)!}[/@]
    [@b.col property="depart" title="院系"]${(directionJournal.depart.name)!}[/@]
    [@b.col width="15%" property="beginOn" title="生效日期"]${directionJournal.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效日期"]${directionJournal.endOn!}[/@]
    [@b.col width="10%" title="已有学生数"]${stdCountMap[directionJournal.level.id?string+"_"+directionJournal.depart.id?string]!0}[/@]
    [@b.col width="10%" property="remark" title="备注"]${directionJournal.remark!}[/@]
  [/@]
[/@]
[@b.foot/]
