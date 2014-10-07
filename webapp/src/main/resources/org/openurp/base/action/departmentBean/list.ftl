[#ftl]
[@b.head/]
[@b.grid items=departmentBeans var="departmentBean"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"][@b.a href="!info?id=${departmentBean.id}"]${departmentBean.code}[/@][/@]
    [@b.col width="20%" property="name" title="部门名称"]${departmentBean.name}[/@]
    [@b.col width="10%" property="teaching" title="是否教学部门"]${(departmentBean.teaching?string("是","否"))!}[/@]
    [@b.col width="10%" property="indexno" title="序号"]${departmentBean.indexno}[/@]
    [@b.col width="20%" property="beginOn" title="起始时间"]${departmentBean.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="结束时间"]${departmentBean.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
