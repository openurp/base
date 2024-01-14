[#ftl]
[@b.head/]
[@b.toolbar title="领导干部信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="officiallist" title="ui.searchForm" theme="search"]
      [@b.textfields names="official.staff.code;工号"/]
      [@b.textfields names="official.staff.name;姓名"/]
      [@b.select name="official.department.id" label="部门" items=departments empty="..." /]
      [@b.textfield name="official.duty" label="职务"/]
      [@b.select label="是否兼任"  name="official.parttime" items={"1":"是", "0":"否"}/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="official.department.code,official.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="officiallist" href="!search?orderBy=official.department.code,official.staff.code&active=1"/]</div>
</div>
[@b.foot/]
