[@b.head/]
[@b.form name="codeListForm" action="!search"]
  [@b.grid items=codes var="code" filterable="true"]
    [@b.gridbar]
      bar.addItem("${b.text("action.new")}",action.add());
      bar.addItem("${b.text("action.modify")}",action.edit());
      bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    [/@]
    [@b.row]
      [@b.boxcol /]
      [@b.col width="15%" property="code" title="代码"]${code.code}[/@]
      [@b.col width="20%" property="name" title="名称"]${code.name}[/@]
      [@b.col property="enName" title="英文名"]${code.enName!}[/@]
      [@b.col width="20%" property="beginOn" title="生效日期"]${code.beginOn!}[/@]
      [@b.col width="20%" property="endOn" title="失效日期"]${code.endOn!}[/@]
    [/@]
  [/@]
[/@]
[@b.foot/]
