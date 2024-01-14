[#ftl]
[@b.head/]
[@b.toolbar title="教室基本信息"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="classroomSearchForm" action="!search" target="classroomlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="classroom.code" label="代码" maxlength="5000"/]
      [@b.textfields names="classroom.name;名称"/]
      [@b.select name="classroom.roomType.id" label="教室类型" items=roomTypes option="id,name" empty="..." /]
      [@b.select name="classroom.campus.id" label="校区" items=campuses option="id,name" empty="..." /]
      [@b.select name="virtual" label="虚拟教室" items={"1":"是", "0":"否"} empty="..." /]
      [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="classroom.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="classroomlist" href="!search?orderBy=classroom.code&active=1"/]
  </div>
</div>
[@b.foot/]
