[#ftl]
[@b.head/]
[@b.grid items=graduateSeasons var="graduateSeason"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${graduateSeason.code}[/@]
    [@b.col property="name" title="名称"/]
    [@b.col width="20%" property="graduateYear" title="毕业年份"/]
    [@b.col width="20%" property="president" title="校长姓名"/]
  [/@]
[/@]
[@b.foot/]
