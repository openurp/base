[#ftl]
[@b.head/]
[@b.toolbar title="人员分类"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="personCategorySearchForm" action="!search" target="personCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="personCategory.name;名称"/]
      [@b.textfields names="personCategory.code;代码"/]
      [@b.textfields names="personCategory.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="personCategory.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="personCategorylist" href="!search?orderBy=personCategory.code"/]
    </td>
  </tr>
</table>
[@b.foot/]