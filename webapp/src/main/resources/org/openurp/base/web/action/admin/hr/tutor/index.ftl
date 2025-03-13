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
      [@b.select name="staff.title.grade.id" label="职称级别" items=professionalGrades empty="..." /]
      [@b.select name="staff.title.id" label="职称" items=titles empty="..." /]
      [@b.select name="staff.degreeLevel.id" label="学位层次" items=degreeLevels empty="..." /]
      [@b.select name="degreeAwardOutside" label="授予单位" items={"0":"本校", "1":"外校"} empty="..."/]
      [@b.select name="stdCount" label="指导学生" items={"0":"无学生", "1":"单个学生","2":"两个学生","3":"三个学生","4":"四个以上"} empty="..."/]
      [@b.select name="major.id" items=majors empty="..." label="专业"/]
      [@b.textfield label="年龄" name="age" placeholder="40-50" /]
      [@b.select name="staff.parttime" label="是否兼职" items={"1":"是", "0":"否"} empty="..." /]
      [@b.select name="staff.status.id" label="状态" items=statuses empty="..." /]
      [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1"/]
      <input type="hidden" name="orderBy" value="staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="tutorlist" href="!search?orderBy=staff.code&active=1"/]</div>
</div>
[@b.foot/]
