[#ftl]
[@b.head/]
[@b.grid items=majors var="major"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"/]
    [@b.col width="20%" property="name" title="名称"/]
    [@b.col property="institution.name" title="所属院校"/]
    [@b.col width="20%" property="category.name" title="学科门类"/]
    [@b.col width="15%" property="beginOn" title="生效时间"/]
    [@b.col width="15%" property="endOn" title="失效时间"/]
  [/@]
[/@]
[@b.foot/]
