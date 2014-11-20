[#ftl]
[@b.head/]
[@b.grid items=teachers var="teacher"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${teacher.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${teacher.id}"]${teacher.name}[/@][/@]
    [@b.col width="15%" property="school" title="毕业学校"]${teacher.school!}[/@]
    [@b.col width="15%" property="department" title="部门"]${teacher.department.name!}[/@]
    [@b.col width="10%" property="beginOn" title="生效时间"]${teacher.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效时间"]${teacher.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
