[#ftl]
[@b.head/]
[@b.toolbar title="领导干部信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="assignmentlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="assignment.staff.code;工号"/]
      [@b.textfields names="assignment.staff.name;姓名"/]
      [@b.select name="assignment.department.id" label="部门" items=departments empty="..." /]
      [@b.select name="assignment.rank.id" label="职务级别" items=ranks empty="..." /]
      [@b.textfield name="assignment.post" label="职务"/]
      [@b.select label="是否正职"  name="assignment.principal" items={"1":"是", "0":"否"}/]
      [@b.select label="是否兼任"  name="assignment.concurrent" items={"1":"是", "0":"否"}/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="assignment.department.code,assignment.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="assignmentlist" href="!search?orderBy=assignment.department.code,assignment.staff.code&active=1"/]</div>
</div>
[@b.foot/]
