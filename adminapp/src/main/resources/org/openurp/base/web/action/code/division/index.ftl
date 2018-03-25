[#ftl]
[@b.head/]
[@b.toolbar title="行政区划类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="divisionSearchForm" action="!search" target="divisionlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="division.code;代码"/]
      [@b.textfields names="division.name;名称"/]
      [@b.textfields names="division.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="division.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="divisionlist" href="!search?orderBy=division.code"/]
    </td>
  </tr>
</table>
[@b.foot/]
