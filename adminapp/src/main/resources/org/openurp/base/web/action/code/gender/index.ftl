[#ftl]
[@b.head/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="genderSearchForm" action="!search" target="genderlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="gender.code;代码"/]
      [@b.textfields names="gender.name;名称"/]
      [@b.textfields names="gender.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="gender.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="genderlist" href="!search?orderBy=gender.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
