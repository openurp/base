[#ftl]
[@b.head/]
[@b.toolbar title="科研机构类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" style="width:200px">
    [@b.form name="institutionSearchForm" action="!search" target="institutionlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="institution.code;代码"/]
      [@b.textfields names="institution.name;名称"/]
      [@b.textfields names="institution.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="institution.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="institutionlist" href="!search?orderBy=institution.code"/]
    </td>
  </tr>
</table>
[@b.foot/]