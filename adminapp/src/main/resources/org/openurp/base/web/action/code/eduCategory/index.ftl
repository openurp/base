[#ftl]
[@b.head/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="eduCategorySearchForm" action="!search" target="eduCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="eduCategory.code;代码"/]
      [@b.textfields names="eduCategory.name;名称"/]
      [@b.textfields names="eduCategory.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="eduCategory.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="eduCategorylist" href="!search?orderBy=eduCategory.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
