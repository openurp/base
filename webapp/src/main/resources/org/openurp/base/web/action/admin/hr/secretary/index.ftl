[#ftl]
[@b.head/]
[@b.toolbar title="教学秘书信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="secretarylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="secretary.staff.code;工号"/]
      [@b.textfields names="secretary.staff.name;姓名"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="secretary.staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="secretarylist" href="!search?orderBy=secretary.staff.code&active=1"/]</div>
</div>
[@b.foot/]
