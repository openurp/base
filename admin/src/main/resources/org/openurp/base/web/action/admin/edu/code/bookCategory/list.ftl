[#ftl]
[@b.head/]
[@b.grid items=bookCategories var="bookCategory"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${bookCategory.code}[/@]
    [@b.col width="35%" property="name" title="名称"][@b.a href="!info?id=${bookCategory.id}"]${bookCategory.name}[/@][/@]
    [@b.col width="25%" property="enName" title="英文名"]${bookCategory.enName!}[/@]
    [@b.col width="10%" property="beginOn" title="生效日期"]${bookCategory.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效日期"]${bookCategory.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
