[#ftl]
[@b.head/]
[#include "nav.ftl"/]

<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="tutorlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="staff.code" label="工号" maxlength="5000"/]
      [@b.textfields names="staff.name;姓名"/]
      [@b.select name="staff.department.id" label="院系" items=departments empty="..." /]
      [@b.select name="staff.tutorType.id" label="导师类别" items=tutorTypes empty="..." /]
      [@b.select name="staff.title.id" label="职称" items=titles empty="..." style="width:100px"/]
      [@b.select name="staff.status.id" label="状态" items=statuses empty="..." style="width:100px"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1"/]
      <input type="hidden" name="orderBy" value="staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="tutorlist" href="!search?orderBy=staff.code&active=1"/]</div>
</div>
[@b.foot/]
