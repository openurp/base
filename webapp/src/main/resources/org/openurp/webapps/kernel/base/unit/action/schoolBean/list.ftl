[#ftl]
[@b.head/]
[@b.grid items=schoolBeans var="schoolBean"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="20%" property="code" title="代码"][@b.a href="!info?id=${schoolBean.id}"]${schoolBean.code}[/@][/@]
    [@b.col width="40%" property="name" title="学校名称"]${schoolBean.name}[/@]
    [@b.col width="30%" property="institution" title="科研机构"]${schoolBean.institution.name!}[/@]
  [/@]
[/@]
[@b.foot/]
