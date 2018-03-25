[#ftl]
[@b.head/]
[@b.toolbar title="代码"/]
<table  class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="metaSearchForm"  action="!search" target="metalist" title="ui.searchForm" theme="search"]
      [@b.textfields names="codeMeta.name;名称"/]
      <input type="hidden" name="orderBy" value="codeMeta.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="metalist" href="!search?orderBy=codeMeta.name" /]</td>
  </tr>
</table>
[@b.foot/]
