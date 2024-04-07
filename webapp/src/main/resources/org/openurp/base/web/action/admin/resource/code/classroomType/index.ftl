[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="classroomTypeSearchForm" action="!search" target="classroomTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="classroomType.code;代码"/]
      [@b.textfields names="classroomType.name;名称"/]
      [@b.textfields names="classroomType.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="classroomType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="classroomTypelist" href="!search?orderBy=classroomType.code&active=1"/]
    </div>
  </div>
[@b.foot/]
