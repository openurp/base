[#ftl]
[@b.head/]
[@b.toolbar title="教职工信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="stafflist" title="ui.searchForm" theme="search"]
      [@b.textfield name="staff.code" label="工号" maxlength="5000"/]
      [@b.textfields names="staff.name;姓名"/]
      [@b.select name="staff.department.id" label="部门" items=departments empty="..." /]
      [@b.select name="staff.staffType.id" label="类别" items=staffTypes empty="..."/]
      [@b.select name="staff.title.id" label="职称" items=titles empty="..."/]
      [@b.select name="staff.status.id" label="状态" items=statuses empty="..."/]
      [@b.select name="staff.formalHr" label="是否在编" items={"1":"是", "0":"否"} empty="..." /]
      [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1"/]
      <input type="hidden" name="orderBy" value="staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stafflist" href="!search?orderBy=staff.code&active=1"/]</div>
</div>
[@b.foot/]
