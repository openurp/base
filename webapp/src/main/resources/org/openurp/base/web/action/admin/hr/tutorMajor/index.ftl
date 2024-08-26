[#ftl]
[@b.head/]
[#include "../tutor/nav.ftl"/]

<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="tutorlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="tutorMajor.staff.code" label="工号" maxlength="50000"/]
      [@b.textfields names="tutorMajor.staff.name;姓名"/]
      [@b.select name="tutorMajor.staff.department.id" label="院系" items=departments empty="..." /]
      [@b.select name="tutorMajor.tutorType.id" label="导师类别" items=tutorTypes empty="..." /]
      [@b.select name="tutorMajor.staff.title.id" label="职称" items=titles empty="..." style="width:100px"/]
      [@b.select name="tutorMajor.major.id" items=majors empty="..." label="专业"/]
      [@b.textfield name="direction.name" label="专业方向"/]
      <input type="hidden" name="orderBy" value="tutorMajor.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="tutorlist" href="!search?orderBy=tutorMajor.staff.code&active=1"/]</div>
</div>
[@b.foot/]
