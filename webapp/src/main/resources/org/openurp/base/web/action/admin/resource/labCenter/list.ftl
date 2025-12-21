[#ftl]
[@b.head/]
[@b.grid items=labCenters var="labCenter"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="name" title="名称"  width="15%"/]
    [@b.col property="shortName" title="简称" width="10%"/]
    [@b.col title="对应学院"]
      [#list labCenter.departs?sort_by('code') as depart]${depart.shortName!depart.name}[#sep],[/#list]
    [/@]
  [/@]
[/@]
[@b.foot/]
