[#ftl]
[@b.head/]
[@b.toolbar title="语种类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="languageSearchForm" action="!search" target="languagelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="language.code;代码"/]
      [@b.textfields names="language.name;名称"/]
      [@b.textfields names="language.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="language.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="languagelist" href="!search?orderBy=language.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
