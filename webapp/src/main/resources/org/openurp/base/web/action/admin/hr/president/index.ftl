[#ftl]
[@b.head/]
[@b.toolbar title="校长信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="presidentlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="president.name;姓名"/]
      [@b.textfields names="president.enName;英文名"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="president.beginOn desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="presidentlist" href="!search?orderBy=president.beginOn desc&active=1"/]</div>
</div>
[@b.foot/]
