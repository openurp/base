[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="countrySearchForm" action="!search" target="countrylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="country.code;代码"/]
      [@b.textfields names="country.name;名称"/]
      [@b.textfields names="country.enName;英文名称"/]
      [@b.textfields names="country.alpha2Code;2位字母代码"/]
      [@b.textfields names="country.alpha3Code;3位字母代码"/]
      <input type="hidden" name="orderBy" value="country.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="countrylist" href="!search?orderBy=country.code"/]
    </div>
  </div>
[@b.foot/]
