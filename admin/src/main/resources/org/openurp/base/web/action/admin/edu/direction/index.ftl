[#ftl]
[@b.head/]
[#include "../major/nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="directionSearchForm" action="!search" target="directionlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="direction.code;代码"/]
      [@b.textfields names="direction.name;名称"/]
      [@b.select name="level.id" label="学历层次" items=levels empty="..." style="width:100px"/]
      [@b.textfields names="direction.major.name;专业名称"/]
      [@b.select name="department.id" label="院系"  items=departs empty="..." style="width:100px"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="direction.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="directionlist" href="!search?active=1&orderBy=direction.code"/]
  </div>
</div>
[@b.foot/]
