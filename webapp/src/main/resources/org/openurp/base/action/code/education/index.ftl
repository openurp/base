[#ftl]
[@b.head/]
[@b.toolbar title="学历类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="educationSearchForm" action="!search" target="educationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="education.name;学历名称"/]
      [@b.textfields names="education.code;代码"/]
      [@b.textfields names="education.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="education.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="educationlist" href="!search?orderBy=education.code"/]
    </td>
  </tr>
</table>
[@b.foot/]