[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="countrySearchForm" action="!search" target="countrylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="country.code;代码"/]
      [@b.textfields names="country.name;名称"/]
      [@b.textfields names="country.enName;英文名称"/]
      [@b.textfields names="country.alpha2Code;2位代码"/]
      [@b.textfields names="country.alpha3Code;3位代码"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="country.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="countrylist" href="!search?orderBy=country.code&active=1"/]
    </div>
  </div>
[@b.foot/]
