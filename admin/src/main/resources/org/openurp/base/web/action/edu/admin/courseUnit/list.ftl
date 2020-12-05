[#ftl]
[@b.head/]
[@b.grid items=courseUnits var="courseUnit"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="indexno" title="代码"]${courseUnit.indexno}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${courseUnit.id}"]${courseUnit.name}[/@][/@]
    [@b.col width="20%" property="setting" title="时间设定"]${courseUnit.setting.name}[/@]
    [@b.col width="20%" property="part" title="时段"]${(courseUnit.part.name)!}[/@]
    [@b.col width="20%" property="beginAt" ]${courseUnit.beginAt}[/@]
    [@b.col width="20%" property="endAt" ]${courseUnit.endAt}[/@]
  [/@]
[/@]
[@b.foot/]
