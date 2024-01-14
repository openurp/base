[#ftl]
[@b.head/]
[@b.toolbar title="辅导员信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="mentorlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="mentor.staff.code;工号"/]
      [@b.textfields names="mentor.staff.name;姓名"/]
      [@b.select name="mentor.department.id" label="院系" items=departments empty="..." /]
      [@b.select name="mentor.staff.title.id" label="职称" items=titles empty="..." style="width:100px"/]
      [@b.select name="mentor.staff.status.id" label="状态" items=statuses empty="..." style="width:100px"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="mentor.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="mentorlist" href="!search?orderBy=mentor.staff.code&active=1"/]</div>
</div>
[@b.foot/]
