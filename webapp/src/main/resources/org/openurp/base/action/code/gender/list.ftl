[#ftl]
[@b.head/]
[@b.grid items=genders var="gender"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${gender.code}[/@]
    [@b.col width="20%" property="name" title="性别名称"][@b.a href="!info?id=${gender.id}"]${gender.name}[/@][/@]
    [@b.col width="20%" property="enName" title="英文名称"]${gender.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${gender.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${gender.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
