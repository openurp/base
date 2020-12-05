[#ftl]
[@b.head/]
[@b.toolbar title="教师信息"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="teacherSearchForm" action="!search" target="teacherlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teacher.user.code;工号"/]
      [@b.textfields names="teacher.user.name;姓名"/]
      [@b.select name="teacher.user.department.id" label="院系" items=departments empty="..." style="width:100px"/]
      [@b.select name="teacher.teacherType.id" label="类型" items=teacherTypes empty="..." style="width:100px"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." /]
      <input type="hidden" name="orderBy" value="teacher.user.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teacherlist" href="!search?orderBy=teacher.user.code"/]</td>
  </tr>
</table>
[@b.foot/]
