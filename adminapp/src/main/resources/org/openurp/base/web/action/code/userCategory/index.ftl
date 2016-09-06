[#ftl]
[@b.head/]
[@b.toolbar title="人员分类"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="userCategorySearchForm" action="!search" target="userCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="userCategory.code;代码"/]
      [@b.textfields names="userCategory.name;名称"/]
      [@b.textfields names="userCategory.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="userCategory.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="userCategorylist" href="!search?orderBy=userCategory.code"/]
    </td>
  </tr>
</table>
[@b.foot/]