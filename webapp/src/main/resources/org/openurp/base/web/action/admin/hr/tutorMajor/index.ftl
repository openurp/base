[#ftl]
[@b.head/]
[#include "../tutor/nav.ftl"/]

<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="tutorlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="tutorMajor.staff.code" label="工号" maxlength="5000"/]
      [@b.textfields names="tutorMajor.staff.name;姓名"/]
      [@b.select name="tutorMajor.staff.department.id" label="院系" items=departments empty="..." /]
      [@b.select name="tutorMajor.tutorType.id" label="导师类别" items=tutorTypes empty="..." /]
      [@b.select name="tutorMajor.staff.title.id" label="职称" items=titles empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="tutorMajor.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="tutorlist" href="!search?orderBy=tutorMajor.staff.code&active=1"/]</div>
</div>
[@b.foot/]
