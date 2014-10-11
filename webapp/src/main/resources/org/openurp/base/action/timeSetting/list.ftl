[#ftl]
[@b.head/]
[@b.grid items=timeSettings var="timeSetting"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="20%" property="name" title="时间设置名称"][@b.a href="!info?id=${timeSetting.id}"]${timeSetting.name}[/@][/@]
    [@b.col width="20%" property="units" title="关联小节"]
      [#list timeSetting.units as unit]
        ${unit.name!}
        [#if schema_has_next]<br>[/#if]
      [/#list]
    [/@]
  [/@]
[/@]
[@b.foot/]
