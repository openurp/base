[#ftl]
[@b.head/]
[@b.grid items=semesters var="semester"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${semester.code}[/@]
    [@b.col width="15%" property="name" title="名称"][@b.a href="!info?id=${semester.id}"]${semester.name}[/@][/@]
    [@b.col width="15%" property="schoolYear" title="学年度"]${semester.schoolYear}[/@]
    [@b.col width="15%" property="calendar" title="日历方案"]${semester.calendar.name}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${semester.beginOn}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${semester.endOn}[/@]
  [/@]
[/@]
[@b.foot/]
