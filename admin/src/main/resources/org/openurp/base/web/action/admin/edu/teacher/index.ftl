[#ftl]
[@b.head/]
[@b.toolbar title="教师信息"]
  bar.addItem("导入","importForm()")
  function importForm(){
    bg.form.submit(document.searchForm,"${b.url('!importForm')}","_blank")
  }
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="teacherlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teacher.staff.code;工号"/]
      [@b.textfields names="teacher.staff.name;姓名"/]
      [@b.select name="teacher.department.id" label="院系" items=departments empty="..." /]
      [#if tutorTypes?size>0]
      [@b.select name="teacher.tutorType.id" label="导师类别" items=tutorTypes empty="..." /]
      [/#if]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." /]
      <input type="hidden" name="orderBy" value="teacher.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teacherlist" href="!search?orderBy=teacher.staff.code"/]</div>
</div>
[@b.foot/]
