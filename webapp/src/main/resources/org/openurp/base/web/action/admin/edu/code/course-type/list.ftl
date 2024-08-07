[#ftl]
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
      [@b.col width="10%" property="code" title="代码"]${code.code}[/@]
      [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${code.id}"]${code.name}[/@][/@]
      [@b.col property="enName" title="英文名"]${code.enName!}[/@]
      [@b.col property="parent" title="上级名称"]${(code.parent.name)!'-'}[/@]
      [@b.col width="13%" property="module.name" title="模块"]${(code.module.name)!}[/@]
      [@b.col width="8%" property="rank.name" title="课程属性"]${(code.rank.name)!}[/@]
      [@b.col width="10%" property="beginOn" title="有效期"]${code.beginOn?string("yyyy-MM")}~${(code.endOn?string("yyyy-MM"))!}[/@]
    [/@]
  [/@]
[/@]
[@b.foot/]
