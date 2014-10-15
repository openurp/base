[#ftl]
[@b.head/]
[@b.toolbar title="学年学期类型"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="semesterSearchForm" action="!search" target="semesterlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="semester.code;代码"/]
      [@b.textfields names="semester.name;名称"/]
      <input type="hidden" name="orderBy" value="semester.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="semesterlist" href="!search?orderBy=semester.code"/]
    </td>
  </tr>
</table>
[@b.foot/]