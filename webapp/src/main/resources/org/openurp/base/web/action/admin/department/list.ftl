[#ftl]
[@b.head/]
[@b.grid items=departments var="department" sortable="false"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("${b.text("action.export")}",action.exportData("code,name,shortName:简称,category.name:类型,"+
                "beginOn:生效日期",
                null,'fileName=部门信息'));
  [/@]
  [@b.row]
    <tr [#if department??] id="${department.indexno}"[/#if]>
    [@b.boxcol /]
    [@b.treecol title="代码、名称"][@b.a href="!info?id=${department.id}"] ${department.code} ${department.name}[/@][/@]
    [@b.col width="8%" property="shortName" title="简称"]${department.shortName!}[/@]
    [@b.col width="10%" property="category" title="类型"]${(department.category.name)!}[/@]
    [@b.col width="7%" property="teaching" title="教学部门"]${(department.teaching?string("是","否"))!}[/@]
    [@b.col width="7%" property="research" title="科研部门"]${(department.research?string("是","否"))!}[/@]
    [@b.col width="12%" title="校区"][#list department.campuses as c]${c.shortName!c.name}[#if c_has_next],[/#if][/#list][/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${department.beginOn!}~${department.endOn!}[/@]
    </tr>
  [/@]
[/@]
[@b.foot/]
