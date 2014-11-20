[#ftl]
[@b.head/]
[@b.toolbar title="教师信息"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="teacherSearchForm" action="!search" target="teacherlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teacher.code;代码"/]
      [@b.textfields names="teacher.name;名称"/]
      [@b.textfields names="teacher.department.name;专业"/]
      <input type="hidden" name="orderBy" value="teacher.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="teacherlist" href="!search?orderBy=teacher.code"/]
    </td>
  </tr>
</table>
[@b.foot/]