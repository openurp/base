[#ftl]
[@b.head/]
[#include "nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="majorSearchForm" action="!search" target="majorlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="major.code;代码"/]
      [@b.textfields names="major.name;名称"/]
      [@b.select name="level.id" label="学历层次"  items=levels empty="..." style="width:100px"/]
      [@b.select name="department.id" label="院系"  items=departs empty="..." style="width:100px"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="major.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="majorlist" href="!search?orderBy=major.code&active=1"/]</div>
</div>
[@b.foot/]
