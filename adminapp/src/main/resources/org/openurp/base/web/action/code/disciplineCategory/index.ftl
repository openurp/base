[#ftl]
[@b.head/]
[@b.toolbar title="学科门类类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="disciplineCategorySearchForm" action="!search" target="disciplineCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="disciplineCategory.code;代码"/]
      [@b.textfields names="disciplineCategory.name;名称"/]
      [@b.textfields names="disciplineCategory.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="disciplineCategory.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="disciplineCategorylist" href="!search?orderBy=disciplineCategory.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
