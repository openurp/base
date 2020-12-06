[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="languageSearchForm" action="!search" target="languagelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="language.code;代码"/]
      [@b.textfields names="language.name;名称"/]
      [@b.textfields names="language.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="language.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="languagelist" href="!search?orderBy=language.code"/]
    </div>
  </div>
[@b.foot/]
