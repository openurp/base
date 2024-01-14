[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="idTypeSearchForm" action="!search" target="idTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="idType.code;代码"/]
      [@b.textfields names="idType.name;名称"/]
      [@b.textfields names="idType.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="idType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="idTypelist" href="!search?orderBy=idType.code&active=1"/]
    </div>
  </div>
[@b.foot/]
