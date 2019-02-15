[#ftl]
[@b.head/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="idTypeSearchForm" action="!search" target="idTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="idType.code;代码"/]
      [@b.textfields names="idType.name;名称"/]
      [@b.textfields names="idType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="idType.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="idTypelist" href="!search?orderBy=idType.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
