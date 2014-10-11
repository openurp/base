[#ftl]
[@b.head/]
[@b.grid items=languages var="language"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${language.code}[/@]
    [@b.col width="20%" property="name" title="语种名称"][@b.a href="!info?id=${language.id}"]${language.name}[/@][/@]
    [@b.col width="20%" property="enName" title="英文名称"]${language.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${language.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${language.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
