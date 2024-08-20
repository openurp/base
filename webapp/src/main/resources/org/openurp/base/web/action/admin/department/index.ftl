[#ftl]
[@b.head/]
[@b.toolbar title="组织机构管理"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="departmentSearchForm" action="!search" target="departmentlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="department.code;代码"/]
      [@b.textfields names="department.name;名称"/]
      [@b.textfields names="department.indexno;序号"/]
      [@b.select name="department.category.id" label="部门类型" items=categories empty="..." /]
      [@b.select name="department.teaching" label="教学部门" items={'true':'是','false':'否'} empty="..."/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="department.indexno"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="departmentlist" href="!search?orderBy=department.indexno&active=1"/]
    </div>
  </div>
[@b.foot/]
