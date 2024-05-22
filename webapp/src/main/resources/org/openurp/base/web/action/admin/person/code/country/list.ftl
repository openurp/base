[#ftl]
[@b.head/]
[@b.form name="codeListForm" action="!search"]
  [@b.grid items=codes var="code" filterable="true"]
    [@b.gridbar]
      bar.addItem("${b.text("action.new")}",action.add());
      bar.addItem("${b.text("action.modify")}",action.edit());
      bar.addItem("${b.text("action.delete")}",action.remove("确认删除？"));
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col width="10%" property="code" title="代码"]${code.code}[/@]
      [@b.col width="15%" property="name" title="名称"]${code.name}[/@]
      [@b.col property="enName" title="英文名称"]${code.enName!}[/@]
      [@b.col width="10%" property="alpha2Code" title="2位字母代码"]${code.alpha2Code}[/@]
      [@b.col width="10%" property="alpha3Code" title="3位字母代码"]${code.alpha3Code}[/@]
      [@b.col width="10%" property="beginOn" title="生效时间"]${code.beginOn!}[/@]
      [@b.col width="10%" property="endOn" title="失效时间"]${code.endOn!}[/@]
    [/@]
  [/@]
[/@]
[@b.foot/]
