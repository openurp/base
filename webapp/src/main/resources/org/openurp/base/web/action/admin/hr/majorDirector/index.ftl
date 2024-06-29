[#ftl]
[@b.head/]
[@b.toolbar title="专业负责人维护"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="directorlist" title="ui.searchForm" theme="search"]
      [@b.textfield name="director.director.staff.code" label="工号" maxlength="5000"/]
      [@b.textfields names="director.director.name;姓名"/]
      [@b.select name="director.director.department.id" label="院系" items=departs empty="..." /]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1"/]
      <input type="hidden" name="orderBy" value="director.major.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="directorlist" href="!search?orderBy=director.major.code&active=1"/]
    </div>
</div>
[@b.foot/]
