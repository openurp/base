[#ftl]
[@b.head/]
[@b.grid items=presses var="press"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${press.code}[/@]
    [@b.col width="25%" property="name" title="名称"][@b.a href="!info?id=${press.id}"]${press.name}[/@][/@]
    [@b.col width="15%" property="enName" title="英文名"]${press.enName!}[/@]
    [@b.col width="20%" property="grade.name" title="级别"/]
    [@b.col width="10%" property="beginOn" title="生效日期"]${press.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效日期"]${press.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
