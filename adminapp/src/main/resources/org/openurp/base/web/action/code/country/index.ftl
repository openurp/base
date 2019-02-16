[#ftl]
[@b.head/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:200px">
    [@b.form name="countrySearchForm" action="!search" target="countrylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="country.code;代码"/]
      [@b.textfields names="country.name;名称"/]
      [@b.textfields names="country.enName;英文名称"/]
      [@b.textfields names="country.alpha2Code;2位字母代码"/]
      [@b.textfields names="country.alpha3Code;3位字母代码"/]
      <input type="hidden" name="orderBy" value="country.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="countrylist" href="!search?orderBy=country.code"/]</td>
  </tr>
</table>
[@b.foot/]
