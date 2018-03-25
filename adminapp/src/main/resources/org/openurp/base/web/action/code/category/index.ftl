[#ftl]
[@b.head/]
[@b.toolbar title="代码分类"/]
<table  class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="categorySearchForm"  action="!search" target="categorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="codeCategory.indexno;序号"/]
      [@b.textfields names="codeCategory.name;名称"/]
      <input type="hidden" name="orderBy" value="codeCategory.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="categorylist" href="!search?orderBy=codeCategory.name" /]</td>
  </tr>
</table>
[@b.foot/]
