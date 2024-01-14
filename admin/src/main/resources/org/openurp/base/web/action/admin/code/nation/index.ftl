[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="nationSearchForm" action="!search" target="nationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="nation.code;代码"/]
      [@b.textfields names="nation.name;名称"/]
      [@b.textfields names="nation.enName;英文名称"/]
      [@b.textfields names="nation.alphaCode;字母代码"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="nation.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="nationlist" href="!search?orderBy=nation.code&active=1"/]
    </div>
  </div>
[@b.foot/]
