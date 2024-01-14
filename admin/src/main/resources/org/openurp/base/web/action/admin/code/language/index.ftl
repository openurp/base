[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="languageSearchForm" action="!search" target="languagelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="language.code;代码"/]
      [@b.textfields names="language.name;名称"/]
      [@b.textfields names="language.enName;英文名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="language.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="languagelist" href="!search?orderBy=language.code&active=1"/]
    </div>
  </div>
[@b.foot/]
