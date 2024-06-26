[#ftl]
[@b.head/]
[@b.toolbar title="教师信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="teacherlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="teacher.staff.code" label="工号" maxlength="5000"/]
      [@b.textfields names="teacher.staff.name;姓名"/]
      [@b.select name="teacher.department.id" label="院系" items=departments empty="..." /]
      [@b.select name="teacher.staff.title.id" label="职称" items=titles empty="..." style="width:100px"/]
      [@b.select name="teacher.staff.status.id" label="状态" items=statuses empty="..." style="width:100px"/]
      [#if teachingOffices?size>0]
      [@b.select name="teacher.office.id" label="教研室" items=teachingOffices empty="..." /]
      [/#if]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1"/]
      <input type="hidden" name="orderBy" value="teacher.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teacherlist" href="!search?orderBy=teacher.staff.code&active=1"/]</div>
</div>
[@b.foot/]
