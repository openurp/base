[#ftl]
[@b.head/]
[#assign typeNames={'boolean':'布尔变量','integer':'整数','float':'浮点数','string':'字符串','json':'JSON'}/]
[@b.grid items=projectProperties var="projectProperty"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="25%" property="name" title="名称"/]
    [@b.col width="30%" property="description" title="描述"/]
    [@b.col width="10%" property="typeName" title="类型"]${typeNames[projectProperty.typeName]!projectProperty.typeName}[/@]
    [@b.col width="30%" property="value" title="值"/]
  [/@]
[/@]
[@b.foot/]
